package dao;

import dto.ClienteFacturacionDTO;
import entity.Cliente;

import java.util.List;


public interface ClienteDao {
    Cliente findById(int id);
    List<Cliente> findAll();
    void create(Cliente cliente);
    void update(Cliente cliente);
    void delete(int id);
    void deleteAll();
    List<ClienteFacturacionDTO> listarClientesXFacturacion();
}
