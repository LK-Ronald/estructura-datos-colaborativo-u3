package com.company.services;

import com.company.exception.ProveedorNotExistException;
import com.company.models.Orden;
import com.company.models.Producto;
import com.company.models.Proveedor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class GestorSistemaServiceTest {
    private final GestorSistemaService gestorSistemaService = new GestorSistemaService();

    @Test
    void shouldCreateAProductIfWereAreInputIsValid() {
        assertDoesNotThrow(() -> gestorSistemaService.agregarProducto("PD2", "ARROZ", "ARROZ DIANA", 2000));
    }

    @Test
    void shouldCreateAProveedorIfWereAreInputIsValid() {
        assertDoesNotThrow(() -> gestorSistemaService.agregarProveedor("P1", "COLANTA", "AVENIDA 13", "colanta@gmail.com"));
    }

    @Test
    void shouldReturnTheProductoIfTheProductExist() {
        Producto producto = gestorSistemaService.buscarProductoPorId("PD1").orElse(null);
        assertNotNull(producto);
    }

    @Test
    void shouldCreateAnOrderIfWereAreInputIsValid() {
        gestorSistemaService.agregarProducto("PD1", "ARROZ", "ARROZ DIANA", 2000);
        gestorSistemaService.agregarProveedor("P2", "COLANTA", "C 13", "colanta@gmail.com");
        List<String> productos = List.of("PD1");
        gestorSistemaService.realizarOrden("alta", "P1", productos);

        List<Orden> o = gestorSistemaService.mostrarOrdenesPorEstado();

        assertEquals(1, o.size());

    }
}
