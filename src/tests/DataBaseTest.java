package tests;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DataBaseTest {

    @Test
    void conectar() {
        String url = "jdbc:mysql://localhost/sistema_registro_renal?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String usuario = "root";
        String password = "admin";
        Connection conexion = null;

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, password);

            if (conexion != null)
                System.out.println("Funciona la conexion a la base de datos");
            else
                System.out.println("Error al conectar la base de datos");


        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println(e);
        }
        System.out.println(conexion);
        //return conexion;
    }
}