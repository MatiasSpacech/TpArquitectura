package utils;

import dao.ClienteDao;
import dao.FacturaDao;
import dao.FacturaProductoDao;
import entity.Cliente;
import entity.Factura;
import entity.FacturaProducto;
import factory.DBType;
import factory.DaoFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import dao.ProductoDao;
import entity.Producto;
import java.io.FileReader;
import java.io.Reader;

public class CargarDatos {
    private final ProductoDao productoDAO;
    private final ClienteDao clienteDAO;
    private final FacturaDao facturaDAO;
    private final FacturaProductoDao facturaProductoDAO;

    public CargarDatos() {
        DaoFactory factoryDAO = DaoFactory.getInstance(DBType.MYSQL);
        this.productoDAO = factoryDAO.createProductoDao();
        this.clienteDAO = factoryDAO.createClienteDao();
        this.facturaDAO = factoryDAO.createFacturaDao();
        this.facturaProductoDAO = factoryDAO.createFacturaProductoDao();
    }

    public void run(){
        cargarProductos("src/main/resources/archivos/productos.csv");
        cargarClientes("src/main/resources/archivos/clientes.csv");
        cargarFacturas("src/main/resources/archivos/facturas.csv");
        cargarFacturaProductos("src/main/resources/archivos/facturas_productos.csv");
    }

    private void cargarProductos(String ubicacion){
        try{
            CSVParser registros = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ubicacion));
            for(CSVRecord registro:registros){
                Producto producto=new Producto(Integer.parseInt(registro.get(0)),
                                                registro.get(1),
                                                Integer.parseInt(registro.get(2)));
                productoDAO.create(producto);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void cargarClientes(String ubicacion){
        try{
            CSVParser registros = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ubicacion));
            for(CSVRecord registro:registros){
                Cliente cliente=new Cliente(Integer.parseInt(registro.get(0)),
                        registro.get(1),
                        registro.get(2));
                clienteDAO.create(cliente);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void cargarFacturas(String ubicacion){
        try{
            CSVParser registros = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ubicacion));
            for(CSVRecord registro:registros){
                Factura factura=new Factura(Integer.parseInt(registro.get(0)),
                        Integer.parseInt(registro.get(1)));

                facturaDAO.create(factura);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void cargarFacturaProductos(String ubicacion){
        try{
            CSVParser registros = CSVFormat.DEFAULT.withHeader().parse(new FileReader(ubicacion));
            for(CSVRecord registro:registros){
                FacturaProducto facturaProducto=new FacturaProducto(Integer.parseInt(registro.get(0)),
                        Integer.parseInt(registro.get(1)),
                        Integer.parseInt(registro.get(2)));

                facturaProductoDAO.create(facturaProducto);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
