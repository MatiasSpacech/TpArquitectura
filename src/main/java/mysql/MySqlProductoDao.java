package mysql;

import dao.ProductoDao;
import dto.ProductoRecaudacionDTO;
import entity.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlProductoDao implements ProductoDao {
    private Connection connection;

    public MySqlProductoDao(Connection connection){
        this.connection=connection;
        createTableIfNotExists();
    }

    private void createTableIfNotExists(){
        String sql="CREATE TABLE IF NOT EXISTS producto(" +
                "id int PRIMARY KEY AUTO_INCREMENT," +
                "nombre varchar(100) not null," +
                "valor decimal(10,2) not null)";
        try{
            Statement st=connection.createStatement();
            st.execute(sql);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Producto> findAll() {
        ArrayList<Producto> productos=new ArrayList<>();
        String sql="SELECT * FROM producto";
        try{
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                Producto producto=new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("valor") );
                productos.add(producto);
            }
            return productos;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Producto findById(int id) {
        String sql="SELECT * FROM producto WHERE id=?";
        try {
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                return new Producto(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("valor"));
            }
            return null;
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Producto producto) {
        String sql="INSERT INTO producto (id,nombre,valor) VALUES (?,?,?)";
        try{
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1,producto.getId());
            ps.setString(2,producto.getNombre());
            ps.setDouble(3,producto.getValor());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Producto producto) {
        String sql="UPDATE producto SET nombre=?,valor=? WHERE id=?";
        try{
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setString(1,producto.getNombre());
            ps.setDouble(2,producto.getValor());
            ps.setInt(3,producto.getId());
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql="DELETE FROM producto WHERE id=?";
        try{
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        String sql="DELETE FROM producto";
        try{
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductoRecaudacionDTO productoMasRecaudo() {
        String sql= "SELECT p.id, p.nombre, SUM(fp.cantidad * p.valor) AS recaudacion " +
                    "FROM factura_producto fp " +
                    "JOIN producto p ON fp.idProducto = p.id " +
                    "GROUP BY p.id, p.nombre " +
                    "ORDER BY recaudacion DESC LIMIT 1";
        try {
            PreparedStatement ps=connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                return new ProductoRecaudacionDTO(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("recaudacion"));
            }
            return null;
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        }


    }
}
