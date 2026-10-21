package com.company.exception;

public class PrecioInvalidoException extends DomainException {
    private static final String MENSAJE = "El precio debe ser mayor a 0";

    private PrecioInvalidoException(String message) {
        super(message);
    }

    public static PrecioInvalidoException precioInvalidoException() {
        throw new PrecioInvalidoException(MENSAJE);
    }

}
