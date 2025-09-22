import dao.ClienteDao;
import dao.ProductoDao;
import dto.ClienteFacturacionDTO;
import dto.ProductoRecaudacionDTO;
import factory.DaoFactory;
import utils.BorrarDatos;
import utils.CargarDatos;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        BorrarDatos borrarDatos = new BorrarDatos();
        borrarDatos.run();


        CargarDatos cargarDatos = new CargarDatos();
        System.out.println("Comenzando a cargar datos...");
        cargarDatos.run();

        DaoFactory daoFactory=DaoFactory.getInstance(factory.DBType.MYSQL);
        ProductoDao productoDao = daoFactory.createProductoDao();
        //Escriba un programa JDBC que retorne el producto que más recaudó. Se define recaudación
        // como cantidad de productos vendidos multiplicado por su valor.
        System.out.println();
        System.out.println();
        System.out.println("////////////////////////////////////////////////////////////////////");
        System.out.println("Producto que más recaudó: ");
        ProductoRecaudacionDTO productoRecaudacionDTO = productoDao.productoMasRecaudo();
        System.out.println(productoRecaudacionDTO);
        System.out.println();
        System.out.println();
        // 4 Escriba un programa JDBC que imprima una lista de clientes, ordenada por a cuál se le
        //facturó más.
        ClienteDao clienteDao=daoFactory.createClienteDao();
        System.out.println("////////////////////////////////////////////////////////////////////");
        System.out.println("Lista de clientes, ordenada por a cuál se le facturó más.");
        List<ClienteFacturacionDTO> clientesXfacturacion= clienteDao.listarClientesXFacturacion();

        for (ClienteFacturacionDTO cliente: clientesXfacturacion) {
            System.out.println(cliente);
        }


//        List<Cliente> clientes=clienteDao.findAll();
//        for(Cliente cliente:clientes){
//            System.out.println(cliente);
//        }
    }
}