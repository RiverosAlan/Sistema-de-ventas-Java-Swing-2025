package Modelos;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/dbsistemasventa";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Método para obtener la conexión
    public static Connection getConnection() {

        Connection connection = null;

        try {

            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            //conectamos a la base datos
            connection = DriverManager.getConnection(URL, USER, PASSWORD);


        } catch (ClassNotFoundException e) {

            System.err.println("Error: No se pudo encontrar el driver de MySQL.");
            e.printStackTrace();

        } catch (SQLException e) {
            System.err.println("Error: No se pudo conectar a la base de datos.");
            e.printStackTrace();
        }

        return connection;
    }

}
