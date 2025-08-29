package org.example.dao;

import org.example.entity.Producto;

import java.util.List;

public interface ProductoDao {
    Producto findById(int id);
    List<Producto> findAll();
    void create(Producto producto);
    void update(Producto producto);
    void delete(int id);
    void deleteAll();
}
