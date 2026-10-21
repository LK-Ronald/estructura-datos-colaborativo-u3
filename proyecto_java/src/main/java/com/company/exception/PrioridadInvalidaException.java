package com.company.exception;

public class PrioridadInvalidaException extends DomainException {
    private static final String MENSAJE = "La prioridad es invalida";

    private PrioridadInvalidaException(String mensaje) {
        super(mensaje);
    }

    public static PrioridadInvalidaException prioridadInvalidaException() {
        throw new PrioridadInvalidaException(MENSAJE);
    }
}
