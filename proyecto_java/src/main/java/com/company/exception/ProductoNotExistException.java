package com.company.exception;

public class ProductoNotExistException extends DomainException {
    private static final String MENSAJE = "El producto con id %s no existe";

    private ProductoNotExistException(String message) {
        super(message);
    }

    public static ProductoNotExistException productoNoExistenteException(String id) {
        return new ProductoNotExistException(String.format(MENSAJE, id));
    }
}
