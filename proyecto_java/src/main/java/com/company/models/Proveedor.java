package com.company.models;

import com.company.exception.CampoObligatorioException;

import java.util.Objects;

public record Proveedor(String nit, String nombre, String direccion, String email) {

    public Proveedor {
        validateNotEmpty(nit, "nit");
        validateNotEmpty(nombre, "nombre");
        validateNotEmpty(direccion, "descripcion");
        validateNotEmpty(email, "email");
    }

    private void validateNotEmpty(final String value, final String fieldName) {
        if (value == null || value.isBlank()) {
            throw CampoObligatorioException.campoObligatorio(fieldName);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Proveedor(String nit1, String nombre1, String direccion1, String email1))) return false;
        return Objects.equals(nit, nit1) &&
                Objects.equals(email, email1) &&
                Objects.equals(nombre, nombre1) &&
                Objects.equals(direccion, direccion1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nit, nombre, direccion, email);
    }
}
