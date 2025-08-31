package org.example.repository.mysql;

import org.example.dao.ProductoDao;
import org.example.entity.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MySqlProductoDao implements ProductoDao {
    private Connection connection;
    public MySqlProductoDao(Connection connection) {
        this.connection = connection;
        crearTablaSiNoExiste();
    }
    private void crearTablaSiNoExiste(){
        final String sql = "CREATE TABLE IF NOT EXISTS producto ("+
                "idProducto int PRIMARY KEY AUTO_INCREMENT,"+
                "nombre VARCHAR(100) NOT NULL," +
                "valor VARCHAR(100) NOT NULL)";
        try (Statement st = this.connection.createStatement()){
            st.execute(sql);
        }
        catch (SQLException e){
            throw new RuntimeException("Error al crear la tabla Producto",e);
        }

    }

    @Override
    public Producto findById(int id) {
        return null;
    }



    @Override
    public void create(Producto producto) {
        final String sql = "INSERT INTO producto (nombre, valor) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getValor());
            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException("Error al crear un Producto",e);
        }
    }

    @Override
    public void update(Producto producto) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void deleteAll() {

    }
}
