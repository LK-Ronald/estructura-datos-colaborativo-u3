package com.company.services;

import com.company.enums.Prioridad;
import com.company.exception.*;
import com.company.models.Orden;
import com.company.models.Producto;
import com.company.models.Proveedor;
import com.company.repository.SistemaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestorSistemaService {
    private final SistemaRepository sistemaRepository = new SistemaRepository();
    private final CalcularTotalService calcularTotalService = new CalcularTotalService();

    public void agregarProveedor(String nit, String nombre, String direccion, String email) {
        Proveedor proveedor = new Proveedor(nit, nombre, direccion, email);
        sistemaRepository.agregarProveedor(proveedor);
    }

    public void agregarProducto(String id, String nombre, String descripcion, double precio) {
        Producto producto = new Producto(id, nombre, descripcion, precio);
        sistemaRepository.agregarProducto(producto);
    }

    public void realizarOrden(String prioridad, String nit, List<String> idsProductos) {
        Proveedor proveedor = sistemaRepository.buscarProveedorPorNit(nit).orElse(null);
        if (proveedor == null) {
            throw ProveedorNotExistException.proveedorNotExistException(nit);
        }
        Prioridad prioridadOrden = Prioridad.fromString(prioridad);
        List<Producto> productos = new ArrayList<>();
        for (String idProducto : idsProductos) {
            Producto producto = sistemaRepository.buscarProductoPorId(idProducto)
                    .orElseThrow(() -> ProductoNotExistException.productoNoExistenteException(idProducto));
            productos.add(producto);
        }
        var orden = new Orden(prioridadOrden, proveedor, productos, calcularTotalService.calcularTotal(productos));
        sistemaRepository.realizarOrden(orden);
    }

    public Optional<Proveedor> buscarProveedorPorNit(final String nit) {
        if (nit == null) {
            throw CampoObligatorioException.campoObligatorio("nit");
        }
        Proveedor proveedor = sistemaRepository.buscarProveedorPorNit(nit).orElse(null);
        if (proveedor == null) {
            return Optional.empty();
        }
        return Optional.of(proveedor);
    }

    public Optional<Producto> buscarProductoPorId(final String id) {
        if (id == null) {
            throw CampoObligatorioException.campoObligatorio("id");
        }
        Producto producto = sistemaRepository.buscarProductoPorId(id).orElse(null);
        if (producto == null) {
            return Optional.empty();
        }
        return Optional.of(producto);
    }

    public Optional<Orden> buscarOrdenPorId(final String nit) {
        if (nit == null) {
            throw CampoObligatorioException.campoObligatorio("nit");
        }
        Orden orden = sistemaRepository.buscarOrdenPorId(nit).orElse(null);
        if (orden == null) {
            return Optional.empty();
        }
        return Optional.of(orden);
    }

    public List<Proveedor> mostrarProveedores() {
        return sistemaRepository.mostrarProveedores();
    }

    public List<Producto> mostrarProductos() {
        return sistemaRepository.mostrarProductos();
    }

    public List<Orden> mostrarOrdenesPorEstado() {
        return sistemaRepository.mostrarOrdenesPorEstado();
    }

    public void procesarOrden() {
        sistemaRepository.procesarOrden();
    }

    public List<Orden> mostrarOrdenesEntregadas() {
        return sistemaRepository.mostrarOrdenesEntregadas();
    }

    public List<Orden> mostrarOrdenesCanceladas() {
        return sistemaRepository.mostrarOrdenesCanceladas();
    }

    public List<Orden> mostrarOrdenesPorPrioridad() {
        return sistemaRepository.mostrarOrdenesPorPrioridad();
    }

    public void eliminarProveedor(final String nit) {
        sistemaRepository.eliminarProveedor(nit);
    }

    public void eliminarProducto(final String id) {
        sistemaRepository.eliminarProducto(id);
    }

    public void cancelarOrden(final String id) {
        sistemaRepository.cancelarOrden(id);
    }

}
