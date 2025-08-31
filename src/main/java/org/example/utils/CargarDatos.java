package org.example.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.dao.ClienteDao;
import org.example.dao.FacturaDao;
import org.example.dao.FacturaProductoDao;
import org.example.dao.ProductoDao;
import org.example.entity.Producto;
import org.example.factory.DBType;
import org.example.factory.DaoFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CargarDatos {
    private final ClienteDao clienteDao;
    private final ProductoDao productoDao;
    private final FacturaDao facturaDao;
    private final FacturaProductoDao facturaProductoDao;

    public CargarDatos() {
        DaoFactory daoFactory = DaoFactory.getInstance(DBType.MYSQL);
        this.clienteDao = daoFactory.createClienteDao();
        this.productoDao = daoFactory.createProductoDao();
        this.facturaDao = daoFactory.createFacturaDao();
        this.facturaProductoDao = daoFactory.createFacturaProductoDao();
    }
    public void run () throws IOException {
       // cargarClientes("/clientes.csv");
        cargarProductos("src/main/resources/productos.csv");
    }

    private void cargarProductos(String path) throws IOException {
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new
                FileReader(path));
        for(CSVRecord row: parser) {
            productoDao.create(new Producto(Integer.parseInt(row.get("idProducto")),row.get("nombre"),Double.parseDouble(row.get("valor"))));

        }
    }

    private void cargarClientes(String path) {

    }
}
