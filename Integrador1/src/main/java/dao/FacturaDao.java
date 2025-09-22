package dao;

import entity.Factura;

import java.util.List;

public interface FacturaDao {
    Factura findByID(int id);
    List<Factura> findAll();
    void create(Factura factura);
    void update(Factura factura);
    void delete(int id);
    void deleteAll();
}
