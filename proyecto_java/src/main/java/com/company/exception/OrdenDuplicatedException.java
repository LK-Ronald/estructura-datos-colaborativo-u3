package com.company.exception;

public class OrdenDuplicatedException extends DomainException {
    private static final String MENSAJE = "La orden ya existe";

    private OrdenDuplicatedException(String message) {
        super(message);
    }

    public static OrdenDuplicatedException ordenDuplicatedException() {
        return new OrdenDuplicatedException(MENSAJE);
    }
}
