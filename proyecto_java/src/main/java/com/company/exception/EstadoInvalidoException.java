package com.company.exception;

public class EstadoInvalidoException extends DomainException {
    private static final String MENSAJE = "La orden fue cancelada";
    private static final String MENSAJE_INVALIDO = "El esto es invalido";
    private static final String MENSAJE_ENTREGADO = "El estado no puede ser cambiado";

    private EstadoInvalidoException(String message) {
        super(message);
    }

    public static EstadoInvalidoException estadoCanceladoException() {
        throw new EstadoInvalidoException(MENSAJE);
    }

    public static EstadoInvalidoException estadoInvalidoException() {
        throw new EstadoInvalidoException(MENSAJE_INVALIDO);
    }

    public static EstadoInvalidoException estadoEntregadoException() {
        throw new EstadoInvalidoException(MENSAJE_ENTREGADO);
    }
}
