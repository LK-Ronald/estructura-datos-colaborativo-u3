package com.company.repository;

import com.company.comparatores.OrdenComparator;
import com.company.comparatores.OrdenEstadoComparator;
import com.company.enums.Estado;
import com.company.exception.*;
import com.company.models.Orden;
import com.company.models.Producto;
import com.company.models.Proveedor;

import java.util.*;

public class SistemaRepository {
    private final Map<String, Proveedor> proveedores = new LinkedHashMap<>();
    private final Map<String, Producto> productos = new LinkedHashMap<>();
    private final OrdenComparator ordenComparator = new OrdenComparator();
    private final OrdenEstadoComparator estadoComparator = new OrdenEstadoComparator();
    private final Queue<Orden> ordenes = new PriorityQueue<>(ordenComparator);
    private final Map<String, Orden> ordenesEntregadas = new HashMap<>();
    private final Map<String, Orden> ordenesCanceladas = new HashMap<>();

    public void agregarProveedor(Proveedor proveedor) {
        if (proveedor == null) {
            throw CampoObligatorioException.campoObligatorio("proveedor");
        }
        if (proveedores.containsKey(proveedor.nit())) {
            throw ProveedorDuplicatedException.proveedorDuplicatedException(proveedor.nit());
        }
        proveedores.put(proveedor.nit(), proveedor);
    }

    public void agregarProducto(Producto producto) {
        if (producto == null) {
            throw CampoObligatorioException.campoObligatorio("producto");
        }
        if (productos.containsKey(producto.id())) {
            throw ProductoDuplicatedException.productoDuplicatedException(producto.id());
        }
        productos.put(producto.id(), producto);
    }

    public void realizarOrden(Orden orden) {
        if (orden == null) {
            throw CampoObligatorioException.campoObligatorio("orden");
        }
        if (ordenes.contains(orden)) {
            throw OrdenDuplicatedException.ordenDuplicatedException();
        }
        if (orden.getProveedor() == null || !proveedores.containsKey(orden.getProveedor().nit())) {
            throw ProveedorNotExistException.proveedorNotExistException(
                    orden.getProveedor() != null ? orden.getProveedor().nit() : ""
            );
        }
        if (orden.getProductos() == null) {
            throw CampoObligatorioException.campoObligatorio("productos");
        }
        orden.getProductos().forEach(producto -> {
            if (!productos.containsKey(producto.id())) {
                throw ProductoNotExistException.productoNoExistenteException(producto.id());
            }
        });
        ordenes.offer(orden);
    }

    public Optional<Proveedor> buscarProveedorPorNit(final String nit) {
        if (nit == null) {
            return Optional.empty();
        }
        Proveedor proveedor = proveedores.get(nit);
        return Optional.ofNullable(proveedor);
    }

    public Optional<Producto> buscarProductoPorId(final String id) {
        if (id == null) {
            return Optional.empty();
        }
        Producto producto = productos.get(id);
        return Optional.ofNullable(producto);
    }

    public Optional<Orden> buscarOrdenPorId(final String id) {
        if (id == null) {
            return Optional.empty();
        }
        return ordenes.stream()
                .filter(o -> id.equals(o.getId()))
                .findFirst();
    }

    public List<Proveedor> mostrarProveedores() {
        return new ArrayList<>(proveedores.values());
    }

    public List<Producto> mostrarProductos() {
        return new ArrayList<>(productos.values());
    }

    public List<Orden> mostrarOrdenesPorEstado() {
        if (ordenes.isEmpty()) {
            return List.of();
        }
        List<Orden> listOrdenes = new ArrayList<>(ordenes);
        listOrdenes.sort(estadoComparator);
        return listOrdenes;
    }

    public void procesarOrden() {
        if (ordenes.isEmpty()) {
            return;
        }
        Orden orden = ordenes.peek();
        switch (orden.getEstado()) {
            case CREADO -> orden.cambiarEstado(Estado.ENVIADO);
            case ENVIADO -> orden.cambiarEstado(Estado.EN_CAMINO);
            case EN_CAMINO -> orden.cambiarEstado(Estado.ENTREGADO);
            default -> {}
        }
        if (orden.getEstado().equals(Estado.ENTREGADO)) {
            ordenes.poll();
            ordenesEntregadas.put(orden.getId(), orden);
        }
    }

    public List<Orden> mostrarOrdenesEntregadas() {
        if (ordenesEntregadas.isEmpty()) {
            return List.of();
        }
        return new ArrayList<>(ordenesEntregadas.values());
    }

    public List<Orden> mostrarOrdenesCanceladas() {
        if (ordenesCanceladas.isEmpty()) {
            return List.of();
        }
        return new ArrayList<>(ordenesCanceladas.values());
    }

    public List<Orden> mostrarOrdenesPorPrioridad() {
        if (ordenes.isEmpty()) {
            return List.of();
        }
        List<Orden> ordenesOrdenadas = new ArrayList<>(ordenes);
        ordenesOrdenadas.sort(ordenComparator);
        return ordenesOrdenadas;
    }

    public void eliminarProveedor(final String nit) {
        if (nit == null) {
            return;
        }
        Proveedor proveedor = proveedores.get(nit);
        if (proveedor == null) {
            return;
        }
        proveedores.remove(nit);
        eliminarProveedorEnOrdenes(nit);
        eliminarProveedorEnOrdenesEntregadas(nit);
        eliminarProveedorEnOrdenesCanceladas(nit);
    }

    public void eliminarProducto(final String id) {
        if (id == null) {
            return;
        }
        Producto producto = productos.get(id);
        if (producto == null) {
            return;
        }
        productos.remove(id);
        eliminarProductoEnOrdenes(id);
        eliminarProductoEnOrdenesEntregadas(id);
        eliminarProductoEnOrdenesCanceladas(id);
    }

    public void cancelarOrden(final String id) {
        if (id == null) {
            return;
        }
        Orden orden = ordenes.stream()
                .filter(o -> id.equals(o.getId()))
                .findFirst()
                .orElse(null);
        if (orden == null) {
            return;
        }
        ordenes.remove(orden);
        orden.cambiarEstado(Estado.CANCELADO);
        ordenesCanceladas.put(orden.getId(), orden);
    }

    private void eliminarProductoEnOrdenes(final String id) {
        ordenes.removeIf(orden ->
                orden.getProductos() != null &&
                orden.getProductos().stream().anyMatch(producto -> id.equals(producto.id())));
    }

    private void eliminarProductoEnOrdenesEntregadas(final String id) {
        ordenesEntregadas.values().removeIf(orden ->
                orden.getProductos() != null &&
                orden.getProductos().stream().anyMatch(producto -> id.equals(producto.id())));
    }

    private void eliminarProductoEnOrdenesCanceladas(final String id) {
        ordenesCanceladas.values().removeIf(orden ->
                orden.getProductos() != null &&
                orden.getProductos().stream().anyMatch(producto -> id.equals(producto.id())));
    }

    private void eliminarProveedorEnOrdenes(final String nit) {
        ordenes.removeIf(orden ->
                orden.getProveedor() != null && nit.equals(orden.getProveedor().nit()));
    }

    private void eliminarProveedorEnOrdenesEntregadas(final String nit) {
        ordenesEntregadas.values().removeIf(orden ->
                orden.getProveedor() != null && nit.equals(orden.getProveedor().nit()));
    }

    private void eliminarProveedorEnOrdenesCanceladas(final String nit) {
        ordenesCanceladas.values().removeIf(orden ->
                orden.getProveedor() != null && nit.equals(orden.getProveedor().nit()));
    }
}
