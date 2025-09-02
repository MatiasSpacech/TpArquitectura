package mysql;

import dao.ClienteDao;
import dto.ClienteFacturacionDTO;
import entity.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlClienteDao implements ClienteDao {
    private Connection connection;

    public MySqlClienteDao(Connection connection) {
        this.connection = connection;
        createTableIfNotExists();
    }

    private void createTableIfNotExists(){
        String sql= "CREATE TABLE IF NOT EXISTS cliente(" +
                    "id INT NOT NULL," +
                    "nombre varchar(100) NOT NULL," +
                    "email varchar(150)," +
                    "PRIMARY KEY(id))";
        try{
            Statement st= connection.createStatement();
            st.execute(sql);
        }catch(SQLException e){
            System.err.println("Error al crear la tabla cliente");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cliente findById(int id) {
        String sql="SELECT * FROM cliente WHERE id=?";
        try{
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cliente> findAll() {
        ArrayList<Cliente> clientes=new ArrayList<>();
        String sql="SELECT * FROM cliente";
        try{
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                Cliente cliente=new Cliente(rs.getInt("id"),
                                            rs.getString("nombre"),
                                            rs.getString("email"));
                clientes.add(cliente);
            }
            return clientes;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Cliente cliente) {
        String sql="INSERT INTO cliente(id,nombre,email) VALUES(?,?,?)";
        try{
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1,cliente.getId());
            ps.setString(2,cliente.getNombre());
            ps.setString(3,cliente.getEmail());
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Cliente cliente) {
        String sql="UPDATE cliente SET nombre=?,email=? WHERE id=?";
        try{
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setString(1,cliente.getNombre());
            ps.setString(2,cliente.getEmail());
            ps.setInt(3,cliente.getId());
            ps.executeUpdate();
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql="DELETE FROM cliente WHERE id=?";
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
        String sql="DELETE FROM cliente";
        try{
            PreparedStatement st=connection.prepareStatement(sql);
            st.executeUpdate();
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ClienteFacturacionDTO> listarClientesXFacturacion() {
        String sql = "SELECT c.id, c.nombre,SUM(fp.cantidad * p.valor) AS totalFacturado " +
                "FROM cliente c " +
                "JOIN factura f ON c.id = f.idCliente " +
                "JOIN factura_producto fp ON f.idFactura = fp.idFactura " +
                "JOIN producto p ON fp.idProducto = p.id " +
                "GROUP BY c.id, c.nombre " +
                "ORDER BY totalFacturado DESC;";
        List<ClienteFacturacionDTO> clientes=new ArrayList<>();
        try{
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                ClienteFacturacionDTO clienteFacturacionDTO=new ClienteFacturacionDTO(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("totalFacturado"));
                clientes.add(clienteFacturacionDTO);
            }
            return clientes;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}
