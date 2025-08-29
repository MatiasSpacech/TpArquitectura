package org.example.dao;

import org.example.entity.Cliente;

import java.util.List;


public interface ClienteDao {
    Cliente findById(int id);
    List<Cliente> findAll();
    void create(Cliente cliente);
    void update(Cliente cliente);
    void delete(int id);
    void deleteAll();
}
