import dao.ClienteDao;
import entity.Cliente;
import factory.DaoFactory;
import utils.BorrarDatos;
import utils.CargarDatos;

import java.io.IOException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        BorrarDatos borrarDatos = new BorrarDatos();
        borrarDatos.run();


        CargarDatos cargarDatos = new CargarDatos();
        System.out.println("Comenzando a cargar datos...");
        cargarDatos.run();

        DaoFactory daoFactory=DaoFactory.getInstance(factory.DBType.MYSQL);
        ClienteDao clienteDao=daoFactory.createClienteDao();
        List<Cliente> clientes=clienteDao.findAll();
        for(Cliente cliente:clientes){
            System.out.println(cliente);
        }
    }
}