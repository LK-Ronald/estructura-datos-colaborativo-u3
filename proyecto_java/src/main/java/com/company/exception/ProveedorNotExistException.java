package com.company.exception;

public class ProveedorNotExistException extends DomainException {
    private static final String MENSAJE = "El proveedor con id %s no existe";

    private ProveedorNotExistException(String message) {
        super(message);
    }

    public static ProveedorNotExistException proveedorNotExistException(String id) {
        return new ProveedorNotExistException(String.format(MENSAJE, id));
    }
}
