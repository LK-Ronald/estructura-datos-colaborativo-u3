package com.company.cli;

import com.company.exception.DomainException;
import com.company.models.Orden;
import com.company.models.Producto;
import com.company.models.Proveedor;
import com.company.services.GestorSistemaService;

import java.util.List;
import java.util.Optional;

public class ControladorAcciones {
    private final GestorSistemaService service;
    private final InputReader reader;

    public ControladorAcciones(GestorSistemaService service, InputReader reader) {
        this.service = service;
        this.reader = reader;
    }

    public void agregarProveedor() {
        try {
            String nit = reader.leerTexto("nit: ");
            String nombre = reader.leerTexto("nombre: ");
            String direccion = reader.leerTexto("direccion: ");
            String email = reader.leerTexto("email: ");
            service.agregarProveedor(nit, nombre, direccion, email);
            System.out.println("[OK] Proveedor agregado.");
        } catch (DomainException e) {
            System.out.println("[Error] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[Error inesperado] " + e.getMessage());
        }
    }

    public void agregarProducto() {
        try {
            String id = reader.leerTexto("id: ");
            String nombre = reader.leerTexto("nombre: ");
            String descripcion = reader.leerTexto("descripcion: ");
            double precio = reader.leerDouble("precio: ");
            service.agregarProducto(id, nombre, descripcion, precio);
            System.out.println("[OK] Producto agregado.");
        } catch (DomainException e) {
            System.out.println("[Error] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[Error inesperado] " + e.getMessage());
        }
    }

    public void realizarOrden() {
        try {
            String prioridad = reader.leerTexto("prioridad (ALTA/MEDIA/BAJA): ");
            String nit = reader.leerTexto("nit (proveedor): ");
            List<String> idsProductos = reader.leerLista("idsProductos (separados por coma): ");
            service.realizarOrden(prioridad, nit, idsProductos);
            System.out.println("[OK] Orden realizada.");
        } catch (DomainException e) {
            System.out.println("[Error] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[Error inesperado] " + e.getMessage());
        }
    }

    public void buscarProveedorPorNit() {
        try {
            String nit = reader.leerTexto("nit: ");
            Optional<Proveedor> prov = service.buscarProveedorPorNit(nit);
            prov.ifPresentOrElse(
                    p -> System.out.println("[Encontrado] " + p),
                    () -> System.out.println("[Info] Proveedor no encontrado.")
            );
        } catch (DomainException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    public void buscarProductoPorId() {
        try {
            String id = reader.leerTexto("id: ");
            Optional<Producto> prod = service.buscarProductoPorId(id);
            prod.ifPresentOrElse(
                    p -> System.out.println("[Encontrado] " + p),
                    () -> System.out.println("[Info] Producto no encontrado.")
            );
        } catch (DomainException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    public void buscarOrdenPorId() {
        try {
            String id = reader.leerTexto("id (orden): ");
            Optional<Orden> orden = service.buscarOrdenPorId(id);
            orden.ifPresentOrElse(
                    o -> System.out.println("[Encontrado] " + o),
                    () -> System.out.println("[Info] Orden no encontrada.")
            );
        } catch (DomainException e) {
            System.out.println("[Error] " + e.getMessage());
        }
    }

    public void mostrarProveedores() {
        List<Proveedor> lista = service.mostrarProveedores();
        if (lista.isEmpty()) {
            System.out.println("[Info] No hay proveedores.");
            return;
        }
        lista.forEach(p -> System.out.println(" - " + p));
    }

    public void mostrarProductos() {
        List<Producto> lista = service.mostrarProductos();
        if (lista.isEmpty()) {
            System.out.println("[Info] No hay productos.");
            return;
        }
        lista.forEach(p -> System.out.println(" - " + p));
    }

    public void mostrarOrdenesPorEstado() {
        List<Orden> lista = service.mostrarOrdenesPorEstado();
        if (lista.isEmpty()) {
            System.out.println("[Info] No hay ordenes.");
            return;
        }
        lista.forEach(o -> System.out.println(" - " + o));
    }

    public void procesarOrden() {
        try {
            service.procesarOrden();
            System.out.println("[OK] Orden procesada.");
        } catch (DomainException e) {
            System.out.println("[Error] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[Error inesperado] " + e.getMessage());
        }
    }

    public void mostrarOrdenesEntregadas() {
        List<Orden> lista = service.mostrarOrdenesEntregadas();
        if (lista.isEmpty()) {
            System.out.println("[Info] No hay ordenes entregadas.");
            return;
        }
        lista.forEach(o -> System.out.println(" - " + o));
    }

    public void mostrarOrdenesCanceladas() {
        List<Orden> lista = service.mostrarOrdenesCanceladas();
        if (lista.isEmpty()) {
            System.out.println("[Info] No hay ordenes canceladas.");
            return;
        }
        lista.forEach(o -> System.out.println(" - " + o));
    }

    public void mostrarOrdenesPorPrioridad() {
        List<Orden> lista = service.mostrarOrdenesPorPrioridad();
        if (lista.isEmpty()) {
            System.out.println("[Info] No hay ordenes pendientes.");
            return;
        }
        lista.forEach(o -> System.out.println(" - " + o));
    }

    public void eliminarProveedor() {
        try {
            String nit = reader.leerTexto("nit: ");
            service.eliminarProveedor(nit);
            System.out.println("[OK] Proveedor eliminado.");
        } catch (DomainException e) {
            System.out.println("[Error] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[Error inesperado] " + e.getMessage());
        }
    }

    public void eliminarProducto() {
        try {
            String id = reader.leerTexto("id: ");
            service.eliminarProducto(id);
            System.out.println("[OK] Producto eliminado.");
        } catch (DomainException e) {
            System.out.println("[Error] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[Error inesperado] " + e.getMessage());
        }
    }

    public void cancelarOrden() {
        try {
            String id = reader.leerTexto("id: ");
            service.cancelarOrden(id);
            System.out.println("[OK] Orden cancelada.");
        } catch (DomainException e) {
            System.out.println("[Error] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[Error inesperado] " + e.getMessage());
        }
    }
}
