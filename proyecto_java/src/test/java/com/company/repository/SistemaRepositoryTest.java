package com.company.repository;

import com.company.comparatores.OrdenComparator;
import com.company.enums.Estado;
import com.company.enums.Prioridad;
import com.company.exception.*;
import com.company.models.Orden;
import com.company.models.Producto;
import com.company.models.Proveedor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GestorSistemaTest {

    private final GestorSistema gestorSistema = new GestorSistema();

    @Test
    void shouldCorrectTheCreationOfAProveedor() {
        var proveedor = new Proveedor("P1", "Colanta", "Avenida 13", "fdsa");
        assertDoesNotThrow(() -> gestorSistema.agregarProveedor(proveedor));
    }

    @Test
    void shouldThrowExceptionIfTheProveedorIsDuplicated() {
        var proveedor = new Proveedor("P1", "Colanta", "Avenida 13", "fdsa");
        gestorSistema.agregarProveedor(proveedor);
        assertThrows(ProveedorDuplicatedException.class, () -> gestorSistema.agregarProveedor(proveedor));
    }

    @Test
    void shouldCorrectTheCreationOfAProducto() {
        var producto = new Producto("PD1", "Jabon", "Jabon de coco", 2000);
        assertDoesNotThrow(() -> gestorSistema.agregarProducto(producto));
    }

    @Test
    void shouldThrowExceptionIfTheProductoIsDuplicated() {
        var producto = new Producto("PD1", "Jabon", "Jabon de coco", 2000);
        gestorSistema.agregarProducto(producto);
        assertThrows(ProductoDuplicatedException.class, () -> gestorSistema.agregarProducto(producto));
    }

    @Test
    void shouldCorrectTheCreationOfAnOrden() {
        List<Producto> productos = new ArrayList<>(
                List.of(
                        new Producto("PD1", "Jabon", "Jabon de coco", 2000)
                )
        );
        var proveedor = new Proveedor("P1", "Colanta", "Avenida 13", "fdsa");

        gestorSistema.agregarProveedor(proveedor);

        gestorSistema.agregarProducto(productos.get(0));

        var orden = new Orden(Prioridad.ALTA, proveedor, productos, calcularTotal(productos));

        assertDoesNotThrow(() -> gestorSistema.realizarOrden(orden));
        assertThrows(OrdenDuplicatedException.class, () -> gestorSistema.realizarOrden(orden));
    }

    @Test
    void shouldThrowExceptionIfTheProveedorIsNotRegistered() {
        List<Producto> productos = new ArrayList<>(
                List.of(
                        new Producto("PD1", "Jabon", "Jabon de coco", 2000)
                )
        );
        var proveedor = new Proveedor("P1", "Colanta", "Avenida 13", "fdsa");

        gestorSistema.agregarProducto(productos.get(0));

        var orden = new Orden(Prioridad.ALTA, proveedor, productos, calcularTotal(productos));

        assertThrows(ProveedorNotExistException.class, () -> gestorSistema.realizarOrden(orden));
    }

    @Test
    void shouldThrowExceptionIfTheProductIsNotRegistered() {
        List<Producto> productos = new ArrayList<>(
                List.of(
                        new Producto("PD1", "Jabon", "Jabon de coco", 2000)
                )
        );
        var proveedor = new Proveedor("P1", "Colanta", "Avenida 13", "fdsa");
        gestorSistema.agregarProveedor(proveedor);
        var orden = new Orden(Prioridad.ALTA, proveedor, productos, calcularTotal(productos));
        assertThrows(ProductoNotExistException.class, () -> gestorSistema.realizarOrden(orden));
    }

    @Test
    void shouldThrowExceptionIfTheListProductIsNotRegistered() {
        List<Producto> productos = new ArrayList<>(
                List.of(
                        new Producto("PD1", "Jabon", "Jabon de coco", 2000),
                        new Producto("PD2", "Arroz", "Arroz roa", 1500),
                        new Producto("PD3", "Huevo", "Huevo doble A", 3000)
                )
        );
        var proveedor = new Proveedor("P1", "Colanta", "Avenida 13", "fdsa");
        gestorSistema.agregarProveedor(proveedor);
        for (var i = 0; i < 2; i++) {
            gestorSistema.agregarProducto(productos.get(i));
        }
        var orden = new Orden(Prioridad.ALTA, proveedor, productos, calcularTotal(productos));
        assertThrows(ProductoNotExistException.class, () -> gestorSistema.realizarOrden(orden));
    }

    @Test
    void shouldReturnIfProveedorExist() {
        Proveedor proveedor = new Proveedor("P1", "Colanta", "Avenida 13", "fdsa");
        gestorSistema.agregarProveedor(proveedor);

        Proveedor busqueda = gestorSistema.buscarProveedorPorNit("P1").orElse(null);

        assertEquals(proveedor, busqueda);
    }

    @Test
    void shouldReturnNullIfProveedorExist() {
        Proveedor busqueda = gestorSistema.buscarProveedorPorNit("P1").orElse(null);
        assertNull(busqueda);
    }

    @Test
    void shouldReturnIfProductExist() {
        Producto producto = new Producto("PD1", "Jabon", "Jabon de coco", 2000);
        gestorSistema.agregarProducto(producto);
        Producto busqueda = gestorSistema.buscarProductoPorNit("PD1").orElse(null);
        assertEquals(producto, busqueda);
    }

    @Test
    void shouldReturnNullIfProductExist() {
        Producto busqueda = gestorSistema.buscarProductoPorNit("PD1").orElse(null);
        assertNull(busqueda);
    }

    @Test
    void shouldReturnIfOrderExist() {
        List<Producto> productos = new ArrayList<>(
                List.of(
                        new Producto("PD1", "Jabon", "Jabon de coco", 2000)
                )
        );
        var proveedor = new Proveedor("P1", "Colanta", "Avenida 13", "fdsa");
        gestorSistema.agregarProveedor(proveedor);
        gestorSistema.agregarProducto(productos.get(0));

        var orden = new Orden(Prioridad.ALTA, proveedor, productos, calcularTotal(productos));
        String idBuscar = orden.getId();
        gestorSistema.realizarOrden(orden);

        Orden busqueda = gestorSistema.buscarOrdenPorNit(idBuscar).orElse(null);
        assertEquals(orden, busqueda);
    }

    @Test
    void shouldReturnNullIfOrderExist() {
        Orden busqueda = gestorSistema.buscarOrdenPorNit("P1").orElse(null);
        assertNull(busqueda);
    }

    @Test
    void shouldReturnListOfProveedores() {
        List<Proveedor> pvs = List.of(
                new Proveedor("P1", "Colanta", "Avenida 13", "fdsa"),
                new Proveedor("P2", "Colanta", "Avenida 13", "fdsa")
        );
        for (var pv : pvs) gestorSistema.agregarProveedor(pv);

        List<Proveedor> proveedores = gestorSistema.mostrarProveedores();
        assertEquals(2, proveedores.size());

        assertArrayEquals(pvs.toArray(), proveedores.toArray());
    }

    @Test
    void shouldReturnListOfProductos() {
        List<Producto> productos = List.of(
                new Producto("PD1", "Jabon", "Jabon de coco", 2000),
                new Producto("PD2", "Arroz", "Arroz roa", 1500),
                new Producto("PD3", "Huevo", "Huevo doble A", 3000)
        );
        for (var pv : productos) gestorSistema.agregarProducto(pv);
        List<Producto> productosMostrados = gestorSistema.mostrarProductos();
        assertEquals(3, productosMostrados.size());
        assertArrayEquals(productos.toArray(), productosMostrados.toArray());
    }

    @Test
    void shouldReturnListOfOrdenes() {
        Proveedor proveedor = new Proveedor("P1", "Colanta", "Avenida 13", "fdsa");
        List<Producto> productos = List.of(
                new Producto("PD1", "Jabon", "Jabon de coco", 2000)
        );
        Orden orden = new Orden(Prioridad.ALTA, proveedor, productos, calcularTotal(productos));
        gestorSistema.agregarProveedor(proveedor);
        gestorSistema.agregarProducto(productos.get(0));
        gestorSistema.realizarOrden(orden);

        List<Orden> listOrdenes = gestorSistema.mostrarOrdenesPorEstado();
        assertEquals(1, listOrdenes.size());

        List<Orden> ordenesOrdenadas = List.of(orden);

        assertArrayEquals(ordenesOrdenadas.toArray(), listOrdenes.toArray());
    }

    @Test
    void shouldChangeTheEstadoOfAnOrder() {
        List<Producto> productos = new ArrayList<>(
                List.of(
                        new Producto("PD1", "Jabon", "Jabon de coco", 2000)
                )
        );
        var proveedor = new Proveedor("P1", "Colanta", "Avenida 13", "fdsa");
        gestorSistema.agregarProveedor(proveedor);
        gestorSistema.agregarProducto(productos.get(0));

        Orden orden = new Orden(Prioridad.ALTA, proveedor, productos, calcularTotal(productos));

        String idOrden = orden.getId();

        gestorSistema.realizarOrden(orden);

        gestorSistema.procesarOrden();
        Orden ordenEnviado = gestorSistema.buscarOrdenPorNit(idOrden).orElse(null);

        assertEquals(Estado.ENVIADO, ordenEnviado.getEstado());

        gestorSistema.procesarOrden();
        Orden ordenEnCamino = gestorSistema.buscarOrdenPorNit(idOrden).orElse(null);
        assertEquals(Estado.EN_CAMINO, ordenEnCamino.getEstado());

        gestorSistema.procesarOrden();

        List<Orden> ordenesEntregadas = gestorSistema.mostrarOrdenesEntregadas();
        assertEquals(1, ordenesEntregadas.size());
        assertEquals(orden, ordenesEntregadas.get(0));

    }

    @Test
    void shouldReturnListOfOrdenesByPriority() {
        List<Producto> productos = new ArrayList<>(
                List.of(
                        new Producto("PD1", "Jabon", "Jabon de coco", 2000)
                )
        );
        var proveedor = new Proveedor("P1", "Colanta", "Avenida 13", "fdsa");
        gestorSistema.agregarProveedor(proveedor);
        gestorSistema.agregarProducto(productos.get(0));

        List<Orden> ordens = new ArrayList<>(
                List.of(
                        new Orden(Prioridad.ALTA, proveedor, productos, 2000),
                        new Orden(Prioridad.BAJA, proveedor, productos, 300),
                        new Orden(Prioridad.MEDIA, proveedor, productos, 2453),
                        new Orden(Prioridad.ALTA, proveedor, productos, 64345),
                        new Orden(Prioridad.BAJA, proveedor, productos, 6745345)
                )
        );

        ordens.forEach(gestorSistema::realizarOrden);

        List<Orden> ordenesOrdenadas = new ArrayList<>(ordens);
        ordenesOrdenadas.sort(new OrdenComparator());

        assertEquals(ordenesOrdenadas, gestorSistema.mostrarOrdenesPorPrioridad());

    }

    @Test
    void shouldReturnListOfOrdenesCanceladas() {
        List<Producto> productos = new ArrayList<>(
                List.of(
                        new Producto("PD1", "Jabon", "Jabon de coco", 2000)
                )
        );
        var proveedor = new Proveedor("P1", "Colanta", "Avenida 13", "fdsa");
        var orden = new Orden(Prioridad.ALTA, proveedor, productos, calcularTotal(productos));
        gestorSistema.agregarProveedor(proveedor);
        gestorSistema.agregarProducto(productos.get(0));
        gestorSistema.realizarOrden(orden);

        gestorSistema.cancelarOrden(orden.getId());

        List<Orden> ordenesCanceladas = gestorSistema.mostrarOrdenesCanceladas();
        assertEquals(1, ordenesCanceladas.size());
        assertEquals(orden, ordenesCanceladas.get(0));
        assertEquals(Estado.CANCELADO, ordenesCanceladas.get(0).getEstado());

        Orden busqueda = gestorSistema.buscarOrdenPorNit(orden.getId()).orElse(null);

        assertNull(busqueda);
    }

    @Test
    void shouldDeleteAnProductoWithIdValid() {
        List<Producto> productos = new ArrayList<>(
                List.of(
                        new Producto("PD1", "Jabon", "Jabon de coco", 2000)
                )
        );
        var proveedor = new Proveedor("P1", "Colanta", "Avenida 13", "fdsa");
        var orden = new Orden(Prioridad.ALTA, proveedor, productos, calcularTotal(productos));
        gestorSistema.agregarProveedor(proveedor);
        gestorSistema.agregarProducto(productos.get(0));
        gestorSistema.realizarOrden(orden);

        gestorSistema.eliminarProducto("PD1");

        Producto pd1 = gestorSistema.buscarProductoPorNit("PD1").orElse(null);
        assertNull(pd1);

        Orden ordenBuscada = gestorSistema.buscarOrdenPorNit(orden.getId()).orElse(null);
        assertNull(ordenBuscada);
    }

    @Test
    void shouldDeleteAnProveedorWithIdValid() {
        List<Producto> productos = new ArrayList<>(
                List.of(
                        new Producto("PD1", "Jabon", "Jabon de coco", 2000)
                )
        );
        var proveedor = new Proveedor("P1", "Colanta", "Avenida 13", "fdsa");
        var orden = new Orden(Prioridad.ALTA, proveedor, productos, calcularTotal(productos));
        gestorSistema.agregarProveedor(proveedor);
        gestorSistema.agregarProducto(productos.get(0));
        gestorSistema.realizarOrden(orden);

        gestorSistema.eliminarProveedor("P1");

        Proveedor proveedorBuscado = gestorSistema.buscarProveedorPorNit("P1").orElse(null);
        assertNull(proveedorBuscado);

        Orden ordenBuscada = gestorSistema.buscarOrdenPorNit(orden.getId()).orElse(null);
        assertNull(ordenBuscada);
    }

    private double calcularTotal(List<Producto> productos) {
        return productos.stream().mapToDouble(Producto::precio).sum();
    }
}
