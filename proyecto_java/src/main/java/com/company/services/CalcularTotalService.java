package com.company.services;

import com.company.models.Producto;

import java.util.List;

public class CalcularTotalService {
    public double calcularTotal(List<Producto> productos) {
        if (productos == null) {
            return 0;
        }
        double total = 0;
        for (Producto producto : productos) {
            total += producto.precio();
        }
        return total;
    }
}
