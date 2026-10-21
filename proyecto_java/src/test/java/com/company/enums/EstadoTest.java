package com.company.enums;

import com.company.exception.EstadoInvalidoException;

public enum Estado {
    CREADO,
    ENVIADO,
    EN_CAMINO,
    ENTREGADO,
    CANCELADO;

    public static Estado fromString(String value) {
        for (Estado e : Estado.values()){
            if(value.equals(e.name())){
                return e;
            }
        }
        throw EstadoInvalidoException.estadoInvalidoException();
    }
}
