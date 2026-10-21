package com.company.comparatores;

import com.company.models.Orden;

import java.util.Comparator;

public class OrdenComparator implements Comparator<Orden> {
    @Override
    public int compare(Orden o1, Orden o2) {
        int priority = Integer.compare(
                o1.getPrioridad().getPriority(),
                o2.getPrioridad().getPriority()
        );

        if (priority != 0) return priority;

        return Double.compare(o1.getTotal(), o2.getTotal());
    }
}
