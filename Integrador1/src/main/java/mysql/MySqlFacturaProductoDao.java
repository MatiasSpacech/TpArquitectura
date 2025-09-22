package mysql;

import dao.FacturaProductoDao;
import entity.Factura;
import entity.FacturaProducto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlFacturaProductoDao implements FacturaProductoDao {
    private Connection connection;

    public MySqlFacturaProductoDao(Connection connection) {
        this.connection = connection;
        createTableIfNotExists();
    }

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS factura_producto(" +
                "idFactura int, " +
                "idProducto int, " +
                "cantidad int, " +
                "primary key(idFactura, idProducto), " +
                "foreign key(idFactura) references factura(idFactura) ON DELETE CASCADE ON UPDATE CASCADE, " +
                "foreign key(idProducto) references producto(id) ON DELETE CASCADE ON UPDATE CASCADE)";
        try {
            Statement st = connection.createStatement();
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
        public FacturaProducto findById(int idFactura, int idProducto) {
            String sql="SELECT * FROM factura_producto WHERE idFactura=? AND id_producto=?";
            try {
                PreparedStatement ps=connection.prepareStatement(sql);
                ps.setInt(1,idFactura);
                ps.setInt(2,idProducto);
                ResultSet rs=ps.executeQuery();
                if(rs.next()){
                    return new FacturaProducto(
                            rs.getInt("idFactura"),
                            rs.getInt("idProducto"),
                            rs.getInt("cantidad")
                    );
                }
                return null;
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }

        @Override
        public List<FacturaProducto> findAll() {
            ArrayList<FacturaProducto> facturas=new ArrayList<>();
            String sql="SELECT * FROM factura_producto";
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    FacturaProducto fp = new FacturaProducto(
                            rs.getInt("idFactura"),
                            rs.getInt("idCliente"),
                            rs.getInt("cantidad")
                    );
                    facturas.add(fp);
                }
                return facturas;
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }

        @Override
        public void create(FacturaProducto fp) {
            String sql="INSERT INTO factura_producto(idFactura,idProducto,cantidad) VALUES(?,?,?)";
            try{
                PreparedStatement ps=connection.prepareStatement(sql);
                ps.setInt(1,fp.getIdFactura());
                ps.setInt(2,fp.getIdProducto());
                ps.setInt(3,fp.getCantidad());
                ps.executeUpdate();
            }catch(SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void update(FacturaProducto fp) {
            String sql="UPDATE factura_producto SET idFactura=?,idProducto=?,cantidad=? WHERE idFactura=? AND idProducto=?";
            try{
                PreparedStatement ps=connection.prepareStatement(sql);
                ps.setInt(1,fp.getIdFactura());
                ps.setInt(2,fp.getIdProducto());
                ps.setInt(3,fp.getCantidad());
                ps.setInt(4,fp.getIdFactura());
                ps.setInt(5,fp.getIdProducto());
                ps.executeUpdate();
            }catch(SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void delete(int idFactura,int idProducto) {
            String sql="DELETE FROM factura_producto WHERE idFactura=? AND idProducto=?";
            try{
                PreparedStatement ps=connection.prepareStatement(sql);
                ps.setInt(1,idFactura);
                ps.setInt(2,idProducto);
                ps.executeUpdate();
            }catch(SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void deleteAll() {
            String sql="DELETE FROM factura_producto";
            try{
                PreparedStatement ps=connection.prepareStatement(sql);
                ps.executeUpdate();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
}

