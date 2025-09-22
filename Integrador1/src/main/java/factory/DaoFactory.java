package factory;

import dao.ClienteDao;
import dao.FacturaDao;
import dao.FacturaProductoDao;
import dao.ProductoDao;
import mysql.MySQLDAOFactory;
//Usamos el patron singleton para asegurarnos una unica instancia
public abstract class DaoFactory {
    private static volatile DaoFactory instance;

    public static DaoFactory getInstance(DBType dbtype){
        if(instance==null){
            synchronized (DaoFactory.class){
                if(instance==null){
                    switch (dbtype){
                        case MYSQL:
                            instance=new MySQLDAOFactory();
                            break;
                        default:
                            throw new IllegalArgumentException("Tipo de base de datos no soportado");
                    }
                }
            }
        }
        return instance;
    }

    public abstract ClienteDao createClienteDao();
    public abstract FacturaDao createFacturaDao();
    public abstract ProductoDao createProductoDao();
    public abstract FacturaProductoDao createFacturaProductoDao();
}
