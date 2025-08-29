package org.example.repository.mysql;

import org.example.dao.ClienteDao;
import org.example.dao.FacturaDao;
import org.example.dao.FacturaProductoDao;
import org.example.dao.ProductoDao;
import org.example.factory.DaoFactory;

public class MySQLDAOFactory extends DaoFactory {

    @Override
    public ClienteDao createClienteDao() {
        return null;
    }

    @Override
    public ProductoDao createProductoDao() {
        return null;
    }

    @Override
    public FacturaDao createFacturaDao() {
        return null;
    }

    @Override
    public FacturaProductoDao createFacturaProductoDao() {
        return null;
    }
}
