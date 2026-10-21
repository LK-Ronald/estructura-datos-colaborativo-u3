package com.company.services;

import com.company.models.Producto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalcularTotalServiceTest {

    CalcularTotalService calcularTotalService = new CalcularTotalService();

    @Test
    void shouldReturnTotal() {
        List<Producto> productos = List.of(
                new Producto("PD1", "Jabon", "Jabon de coco", 2000),
                new Producto("PD2", "ARROZ", "Arroz roa", 1500),
                new Producto("PD2", "ARROZ", "Arroz roa", 1500),
                new Producto("PD2", "ARROZ", "Arroz roa", 1500),
                new Producto("PD2", "ARROZ", "Arroz roa", 1500)
        );

        double total = calcularTotalService.calcularTotal(productos);

        assertEquals(8000, total);
    }
}
