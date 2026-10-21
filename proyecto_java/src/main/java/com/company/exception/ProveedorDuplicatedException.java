package com.company.exception;

public class ProveedorDuplicatedException extends DomainException {
    private static final String MENSAJE = "El proveedor con id %s ya existe";

    private ProveedorDuplicatedException(String message) {
        super(message);
    }

    public static ProveedorDuplicatedException proveedorDuplicatedException(String id) {
        return new ProveedorDuplicatedException(String.format(MENSAJE, id));
    }
}
