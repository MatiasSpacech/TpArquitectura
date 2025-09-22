package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerMySql {
    private static volatile ConnectionManagerMySql instance;
    private Connection connection;
    private static final String URL="jdbc:mysql://localhost:3306/integrador1";
    private static final String USER="root";
    private static final String PASSWORD="";

    private ConnectionManagerMySql(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Conectado a la base de datos");
        }catch(SQLException e){
            System.err.println(e.getMessage()+" Error al conectar a la base de datos");
        }catch(ClassNotFoundException e){
            System.err.println(e.getMessage()+" No se encontro el driver de MySQL");
        }
    }

    public static ConnectionManagerMySql getInstance(){
        if(instance==null){
            synchronized (ConnectionManagerMySql.class){
                if(instance==null){
                    instance=new ConnectionManagerMySql();
                }
            }
        }
        return instance;
    }

    public Connection getConnection(){
        return this.connection;
    }
}
