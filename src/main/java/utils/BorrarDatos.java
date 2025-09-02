package utils;

import dao.ClienteDao;
import dao.FacturaDao;
import dao.FacturaProductoDao;
import dao.ProductoDao;
import factory.DBType;
import factory.DaoFactory;

public class BorrarDatos {
    private final ProductoDao productoDAO;
    private final ClienteDao clienteDAO;
    private final FacturaDao facturaDAO;
    private final FacturaProductoDao facturaProductoDAO;

    public BorrarDatos(){
        DaoFactory factoryDAO=DaoFactory.getInstance(DBType.MYSQL);
        this.productoDAO=factoryDAO.createProductoDao();
        this.clienteDAO=factoryDAO.createClienteDao();
        this.facturaDAO=factoryDAO.createFacturaDao();
        this.facturaProductoDAO=factoryDAO.createFacturaProductoDao();
    }

    public void run(){
        this.productoDAO.deleteAll();
        this.clienteDAO.deleteAll();
        this.facturaDAO.deleteAll();
        this.facturaProductoDAO.deleteAll();

        System.out.println("Borrando datos de la tabla productos...");
        System.out.println("Borrando datos de la tabla clientes...");
        System.out.println("Borrando datos de la tabla facturas...");
        System.out.println("Borrando datos de la tabla facturasProductos...");
    }
}
