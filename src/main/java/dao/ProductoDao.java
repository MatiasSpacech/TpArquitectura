package dao;

import dto.ProductoRecaudacionDTO;
import entity.Producto;

import java.util.List;

public interface ProductoDao {
    Producto findById(int id);
    //List<Producto> findAll();
    void create(Producto producto);
    void update(Producto producto);
    void delete(int id);
    void deleteAll();
    ProductoRecaudacionDTO productoMasRecaudo();
}
