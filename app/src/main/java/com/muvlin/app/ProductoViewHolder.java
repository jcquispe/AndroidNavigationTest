package com.muvlin.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muvlin.app.R;

public class ProductoViewHolder extends RecyclerView.ViewHolder {

    private final TextView textDescripcion;
    private final TextView textCodigo;
    private final TextView textCantidad;
    private final TextView textUnitario;
    private final TextView textPrecio;

    public ProductoViewHolder(@NonNull View itemView) {
        super(itemView);
        textCodigo = itemView.findViewById(R.id.textCodigo);
        textDescripcion = itemView.findViewById(R.id.textDescripcion);
        textCantidad = itemView.findViewById(R.id.textCantidad);
        textUnitario = itemView.findViewById(R.id.textUnitario);
        textPrecio = itemView.findViewById(R.id.textPrecio);
    }

    public void bind(String codigo, String descripcion, Integer cantidad, Double costo, Double precio) {
        Double total = 0.0;
        textCodigo.setText("COD: " + codigo);
        textDescripcion.setText(descripcion);
        textCantidad.setText(String.valueOf(cantidad));
        Calculos calcular = new Calculos();
        Double p = calcular.calculaPrecio(costo, SharedPreferencesManager.getInstance().getMargen());
        textUnitario.setText(String.valueOf(p));
        textPrecio.setText(String.valueOf(Math.round((p * cantidad) * 100.0) / 100.0));
    }

    public static ProductoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ProductoViewHolder(view);
    }
}
