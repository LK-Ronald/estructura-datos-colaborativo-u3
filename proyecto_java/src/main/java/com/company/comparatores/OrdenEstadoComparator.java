package com.company.comparatores;

import com.company.models.Orden;

import java.util.Comparator;

public class OrdenEstadoComparator implements Comparator<Orden> {
    @Override
    public int compare(Orden o1, Orden o2) {
        return o1.getEstado().compareTo(o2.getEstado());
    }
}
