package org.example.dao;

import org.example.entity.FacturaProducto;

import java.util.List;

public interface FacturaProductoDao {
    FacturaProducto findById(int id);
    List<FacturaProducto> findAll();
    void create(FacturaProducto producto);
    void update(FacturaProducto producto);
    void delete(FacturaProducto producto);
    void deleteAll();
}
