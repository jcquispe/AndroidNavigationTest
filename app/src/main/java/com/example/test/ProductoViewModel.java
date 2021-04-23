package com.example.test;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.test.database.model.Producto;
import com.example.test.database.repository.ProductoRepository;

import java.util.List;

public class ProductoViewModel extends AndroidViewModel {
    private ProductoRepository mRepository;
    private final LiveData<List<Producto>> mAllProductos;

    public ProductoViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ProductoRepository(application);
        mAllProductos = mRepository.getListaProductos();
    }

    public LiveData<List<Producto>> getAllProductos() {
        return mAllProductos;
    }

    public void insert(Producto producto) {
        mRepository.insert(producto);
    }
}
