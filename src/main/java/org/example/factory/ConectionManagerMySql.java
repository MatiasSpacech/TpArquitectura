package org.example.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionManagerMySql {

    private static volatile ConectionManagerMySql instance;
    private Connection connection;
    private final static String URL = "jdbc:mysql://localhost:3306/tpintegrador?createDatabaseIfNotExist=true";
    private final static String USER = "root";
    private final static String PASS = "";

    private ConectionManagerMySql() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conectado! a la base de datos");
        } catch (ClassNotFoundException e) {
           System.err.println(e.getMessage() + "No se encontro el driver de MySQL");
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "error al conectar a Base de datos");
        }
    }
    public static ConectionManagerMySql getInstance() {
        if (instance == null) {
            synchronized (ConectionManagerMySql.class) {
                if (instance == null) {
                    instance = new ConectionManagerMySql();
                }
            }
        }
        return instance;
    }
}
