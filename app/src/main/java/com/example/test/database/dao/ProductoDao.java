package com.example.test.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.test.database.model.Producto;

import java.util.List;

@Dao
public interface ProductoDao {
    @Query("SELECT * FROM producto")
    LiveData<List<Producto>> getAll();

    @Query("SELECT * FROM producto WHERE pid IN (:productoIds)")
    List<Producto> loadAllByIds(Integer[] productoIds);

    @Query("SELECT * FROM producto WHERE descripcion LIKE :descripcion LIMIT 1")
    Producto findByDescripcion(String descripcion);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Producto... productos);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Producto producto);

    @Delete
    void delete(Producto producto);

    @Query("DELETE FROM producto")
    void deleteAll();
}
