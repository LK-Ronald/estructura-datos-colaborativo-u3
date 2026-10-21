package com.company.exception;

public class ProductoDuplicatedException extends DomainException {
    private static final String MENSAJE = "El producto con id %s ya existe";

    private ProductoDuplicatedException(String message) {
        super(message);
    }

    public static ProductoDuplicatedException productoDuplicatedException(String id) {
        return new ProductoDuplicatedException(String.format(MENSAJE, id));
    }
}
