package com.company.models;

import com.company.exception.CampoObligatorioException;
import com.company.exception.PrecioInvalidoException;

public record Producto(String nombre, String descripcion, double precio) {

    public Producto {
        validateNotEmpty(nombre, "nombre");
        validateNotEmpty(descripcion, "descripcion");
        validatePrice(precio);
    }

    private void validateNotEmpty(final String value, final String fieldName) {
        if (value == null || value.isBlank()) {
            throw CampoObligatorioException.campoObligatorio(fieldName);
        }
    }

    private void validatePrice(final double value) {
        if (value <= 0) {
            throw PrecioInvalidoException.precioInvalidoException();
        }
    }
}
