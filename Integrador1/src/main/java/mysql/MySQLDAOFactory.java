package mysql;

import dao.ClienteDao;
import dao.FacturaDao;
import dao.FacturaProductoDao;
import dao.ProductoDao;
import factory.ConnectionManagerMySql;
import factory.DaoFactory;

import java.sql.Connection;

public class MySQLDAOFactory extends DaoFactory {
    private Connection connection;

    public MySQLDAOFactory(){
        this.connection= ConnectionManagerMySql.getInstance().getConnection();
    }

    @Override
    public ClienteDao createClienteDao() {
        return new MySqlClienteDao(connection);
    }

    @Override
    public ProductoDao createProductoDao() {
        return new MySqlProductoDao(connection);
    }

    @Override
    public FacturaDao createFacturaDao() {
        return new MySqlFacturaDao(connection);
    }

    @Override
    public FacturaProductoDao createFacturaProductoDao() {
        return new MySqlFacturaProductoDao(connection);
    }
}
