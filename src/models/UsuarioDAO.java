package models;

import core.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO
{
    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;

    public static List<Usuario> getUsuarios()
    {
        List<Usuario> lista = new ArrayList<>();
        String consulta = "SELECT * FROM usuario";

        try
        {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(consulta);
            resultado = sentencia.executeQuery();

            while (resultado.next())
            {
                Usuario dato = new Usuario(
                    resultado.getInt("id"),
                    resultado.getString("rut"),
                    resultado.getString("contrasena"),
                    resultado.getString("nombre"),
                    resultado.getString("direccion"),
                    resultado.getString("email"),
                    resultado.getString("telefono"),
                    resultado.getString("fechaCreacion")
                );

                lista.add(dato);
            }
        }
        catch (Exception e) {}

        return lista;
    }
}
