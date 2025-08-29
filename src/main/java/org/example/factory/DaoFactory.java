package org.example.factory;

import org.example.dao.ClienteDao;
import org.example.dao.FacturaDao;
import org.example.dao.FacturaProductoDao;
import org.example.dao.ProductoDao;
import org.example.repository.mysql.MySQLDAOFactory;

public abstract class DaoFactory {
    private volatile static DaoFactory instance;

    public static DaoFactory getInstance(DBType dbType) {
        if (instance == null) {
            synchronized (DaoFactory.class) {
                if (instance == null) {
                    switch (dbType) {
                        case MYSQL:
                            instance = new MySQLDAOFactory();
                            break;
                        //case DERBY:
                        //     instance = new DerbyDAOFactoty();
                        //     break;
                        default:
                            throw new IllegalArgumentException("DBType desconocido: " + dbType);
                    }
                }
            }
        }
        return instance;
    }
    public abstract ClienteDao createClienteDao();
    public abstract ProductoDao createProductoDao();
    public abstract FacturaDao createFacturaDao();
    public abstract FacturaProductoDao createFacturaProductoDao();
}
