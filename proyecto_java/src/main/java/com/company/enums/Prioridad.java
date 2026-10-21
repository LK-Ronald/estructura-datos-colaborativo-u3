package com.company.enums;

import com.company.exception.PrioridadInvalidaException;

public enum Prioridad {
    ALTA(1),
    MEDIA(2),
    BAJA(3);

    private final int priority;

    Prioridad(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public static Prioridad fromString(final String value) {
        for (Prioridad p : Prioridad.values()) {
            if (value.equalsIgnoreCase(p.name())) {
                return p;
            }
        }
        throw PrioridadInvalidaException.prioridadInvalidaException();
    }
}
