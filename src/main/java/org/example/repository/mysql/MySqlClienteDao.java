package org.example.repository.mysql;

import org.example.dao.ClienteDao;
import org.example.entity.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlClienteDao implements ClienteDao {
    private Connection connection;

    public MySqlClienteDao(Connection connection) {
            this.connection = connection;
            crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste(){
       final String sql = "CREATE TABLE IF NOT EXISTS cliente ("+
                        "idCliente int PRIMARY KEY AUTO_INCREMENT,"+
                        "nombre VARCHAR(100) NOT NULL," +
                        "email VARCHAR(100) NOT NULL UNIQUE)";
       try (Statement st = connection.createStatement()){
           st.execute(sql);
       }
       catch (SQLException e){
          throw new RuntimeException("Error al crear la tabla Cliente",e);
       }

    }

    @Override
    public Cliente findById(int id) {
        final String sql = "SELECT * FROM cliente WHERE idCliente = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("idCliente"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setEmail(rs.getString("email"));
                    return cliente;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("Error al obtener el cliente",e);
        }
        return null;
    }

    @Override
    public List<Cliente> findAll() {
        final String sql = "SELECT * FROM cliente";
        List<Cliente> clientes = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("idCliente"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setEmail(rs.getString("email"));
                    clientes.add(cliente);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("Error al obtener los clientes",e);
        }
        return clientes;
    }

    @Override
    public void create(Cliente cliente) {
        final String sql = "INSERT INTO cliente (nombre, email) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getEmail());
            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException("Error al crear un cliente",e);
        }

    }

    @Override
    public void update(Cliente cliente) {
        final String sql = "UPDATE cliente SET nombre=?, email=? WHERE idCliente=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getEmail());
            ps.setInt(3, cliente.getIdCliente());
            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException("Error al actualizar un cliente",e);
}
    }

    @Override
    public void delete(int id) {
        final String sql = "DELETE FROM cliente WHERE idCliente=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Error al eliminar un cliente",e);
        }
    }

    @Override
    public void deleteAll() {
        final String sql = "DELETE FROM cliente";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException("Error al eliminar los clientes",e);
        }
    }
}
