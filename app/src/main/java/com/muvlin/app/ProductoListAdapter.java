package com.muvlin.app;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.muvlin.app.database.model.Producto;

public class ProductoListAdapter extends ListAdapter<Producto, ProductoViewHolder> {

    public ProductoListAdapter(@NonNull DiffUtil.ItemCallback<Producto> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ProductoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto current = getItem(position);
        holder.bind(current.getCodigo(), current.getDescripcion(), current.getCantidad(), current.getCosto());
    }

    public static class ProductoDiff extends DiffUtil.ItemCallback<Producto> {

        @Override
        public boolean areItemsTheSame(@NonNull Producto oldItem, @NonNull Producto newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Producto oldItem, @NonNull Producto newItem) {
            return oldItem.getDescripcion().equals(newItem.getDescripcion());
        }
    }
}
