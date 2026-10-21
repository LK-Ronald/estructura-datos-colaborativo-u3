package com.company.models;

import com.company.enums.Estado;
import com.company.enums.Prioridad;
import com.company.exception.CampoObligatorioException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class Orden {
    private final String id;
    private Estado estado;
    private Prioridad prioridad;
    private final Proveedor proveedor;
    private final List<Producto> productos;
    private final double total;
    private final LocalDateTime fecha;

    public Orden(Prioridad prioridad, Proveedor proveedor, List<Producto> productos, double total) {
        this.id = UUID.randomUUID().toString();
        this.estado = Estado.CREADO;
        this.prioridad = prioridad;
        this.proveedor = proveedor;
        this.productos = productos;
        this.total = total;
        this.fecha = LocalDateTime.now();
    }

    public void cambiarEstado(Estado estado) {
        if (estado == null) {
            CampoObligatorioException.campoObligatorio("estado");
        }
        if (this.estado.equals(Estado.CANCELADO)) {
            return;
        }
        this.estado = estado;
    }

    public void cambiarPrioridad(Prioridad prioridad) {
        if (prioridad == null) {
            CampoObligatorioException.campoObligatorio("prioridad");
        }
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "Orden{" +
                "id='" + id + '\'' +
                ", estado=" + estado +
                ", prioridad=" + prioridad +
                ", proveedor=" + proveedor +
                ", productos=" + productos +
                ", total=" + total +
                ", fecha=" + fecha +
                '}';
    }
}
