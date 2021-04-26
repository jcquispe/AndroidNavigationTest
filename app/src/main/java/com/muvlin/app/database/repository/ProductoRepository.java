package com.muvlin.app.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.muvlin.app.Calculos;
import com.muvlin.app.SharedPreferencesManager;
import com.muvlin.app.database.AppDatabase;
import com.muvlin.app.database.dao.ProductoDao;
import com.muvlin.app.database.model.Producto;

import java.util.List;

public class ProductoRepository {
    private ProductoDao productoDao;
    private LiveData<List<Producto>> listaProductos;

    public ProductoRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        productoDao = db.productoDao();
        listaProductos = productoDao.getAll();
    }

    public LiveData<List<Producto>> getListaProductos() {
        return listaProductos;
    }

    public void insert(Producto producto) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            productoDao.insert(producto);
        });
    }

    public Double getTotal(){
        Double total = 0.0;
        List<Producto>  listAllProducto = productoDao.listAll();
        for (Producto p: listAllProducto) {
            Calculos calcular = new Calculos();
            total += calcular.calculaPrecio(p.getCosto(), SharedPreferencesManager.getInstance().getMargen());;
        }
        return total;
    }
}
