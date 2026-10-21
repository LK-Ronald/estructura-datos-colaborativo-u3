package com.company.models;

import com.company.exception.CampoObligatorioException;

public record Proveedor(String nombre, String direccion, String email) {

    public Proveedor {
        validateNotEmpty(nombre, "nombre");
        validateNotEmpty(direccion, "descripcion");
        validateNotEmpty(email, "email");
    }

    private void validateNotEmpty(final String value, final String fieldName) {
        if (value == null || value.isBlank()) {
            throw CampoObligatorioException.campoObligatorio(fieldName);
        }
    }
}
