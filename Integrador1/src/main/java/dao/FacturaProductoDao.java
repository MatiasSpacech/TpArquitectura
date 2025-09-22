package dao;

import entity.FacturaProducto;

import java.util.List;

public interface FacturaProductoDao {
    FacturaProducto findById(int idFactura,int idProducto);
    List<FacturaProducto> findAll();
    void create(FacturaProducto fp);
    void update(FacturaProducto fp);
    void delete(int idFactura,int idProducto);
    void deleteAll();
}
