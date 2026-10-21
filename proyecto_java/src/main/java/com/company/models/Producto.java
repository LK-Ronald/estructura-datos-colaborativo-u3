package com.company.models;

import com.company.exception.CampoObligatorioException;
import com.company.exception.PrecioInvalidoException;

import java.util.Objects;

public record Producto(String id, String nombre, String descripcion, double precio) {

    public Producto {
        validateNotEmpty(id, "id");
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Producto(String id1, String nombre1, String descripcion1, double precio1))) return false;
        return Double.compare(precio, precio1) == 0 &&
                Objects.equals(id, id1) &&
                Objects.equals(nombre, nombre1) &&
                Objects.equals(descripcion, descripcion1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion, precio);
    }
}
