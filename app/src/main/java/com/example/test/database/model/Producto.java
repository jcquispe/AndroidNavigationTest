package com.example.test.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Producto {
    @PrimaryKey(autoGenerate = true)
    private Integer pid;
    @ColumnInfo(name = "codigo")
    private String codigo;
    @ColumnInfo(name = "descripcion")
    private String descripcion;
    @ColumnInfo(name = "cantidad")
    private Integer cantidad;
    @ColumnInfo(name = "costo")
    private Double costo;

    public Producto(@NonNull String codigo, @NonNull String descripcion, @NonNull Integer cantidad, @NonNull Double costo) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.costo = costo;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Double getCosto() {
        return costo;
    }
}

