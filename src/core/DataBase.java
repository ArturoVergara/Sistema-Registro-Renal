package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBase
{
    public static Connection conectar()
    {
        String url = "jdbc:mysql://localhost/sistema_registro_renal";
        String usuario = "root";
        String password = "";
        Connection conexion = null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
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

        return conexion;
    }
}
