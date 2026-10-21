package com.company.exception;

public class CampoObligatorioException extends DomainException {
    private static final String MENSAJE = "Este campo es obligatorio";

    private CampoObligatorioException(String message) {
        super(message);
    }

    public static CampoObligatorioException campoObligatorio(final String campo) {
        throw new CampoObligatorioException(MENSAJE + ": " + campo);
    }
}
