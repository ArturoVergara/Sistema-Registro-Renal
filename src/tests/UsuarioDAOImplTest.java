package tests;

import core.DataBase;
import models.Usuario;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;


class UsuarioDAOImplTest {

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    public String query;

    @org.junit.jupiter.api.Test
    void getUsuario() {
        String rut = "111";
        query = "SELECT * FROM usuario WHERE rut=?";
        Usuario usuarioRetorno = null;
        try
        {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,rut);
            resultado = sentencia.executeQuery();

            while (resultado.next())
            {
                Date date = resultado.getDate("fechaCreacion"); // se obtiene la fecha-hora de la db
                Timestamp timestamp = new Timestamp(date.getTime());      // pasamos la fecha-hora a un formato en java
                Usuario dato = new Usuario(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("contrasena"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime()
                );
                usuarioRetorno = dato;
                out.println(dato.getNombre() + dato.getDireccion());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        out.println(usuarioRetorno);
        //return usuarioRetorno;
    }

    @org.testng.annotations.Test
    void testGetUsuario() {
    }

    @org.junit.jupiter.api.Test
    void getUsuarios() {
        List<Usuario> list = new ArrayList<>();
        query = "SELECT * FROM sistema_registro_renal.usuario;";
        try
        {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            resultado = sentencia.executeQuery();

            while (resultado.next())
            {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Usuario dato = new Usuario(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("contrasena"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime()
                );
                list.add(dato);
            }
        }
        catch (SQLException sqlException) {
            sqlException.getErrorCode();
            sqlException.getCause();
        }
        for (Usuario usuario : list) {
            System.out.println(usuario);
        }
    }

    @org.junit.jupiter.api.Test
    void createUsuario() {
    }

    @org.junit.jupiter.api.Test
    void deleteUsuario() {
    }
}