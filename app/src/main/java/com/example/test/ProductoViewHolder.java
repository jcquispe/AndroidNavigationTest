package com.example.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductoViewHolder extends RecyclerView.ViewHolder {

    private final TextView textDescripcion;
    private final TextView textCodigo;
    private final TextView textCantidad;
    private final TextView textCosto;

    public ProductoViewHolder(@NonNull View itemView) {
        super(itemView);
        textCodigo = itemView.findViewById(R.id.textCodigo);
        textDescripcion = itemView.findViewById(R.id.textDescripcion);
        textCantidad = itemView.findViewById(R.id.textCantidad);
        textCosto = itemView.findViewById(R.id.textCosto);
    }

    public void bind(String codigo, String descripcion, Integer cantidad, Double costo) {
        textCodigo.setText(codigo);
        textDescripcion.setText(descripcion);
        textCantidad.setText(String.valueOf(cantidad));
        textCosto.setText(String.valueOf(costo));
    }

    public static ProductoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ProductoViewHolder(view);
    }
}
