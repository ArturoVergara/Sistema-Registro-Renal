package DAO;

import core.DataBase;
import models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    public String query;

    @Override
    public Usuario getUsuario(String rut)
    {
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
                Date date = resultado.getDate("DispatchDate"); // se obtiene la fecha-hora de la db
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
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return usuarioRetorno;
    }

    @Override
    public Usuario getUsuario(int id) {
        return null;
    }

    @Override
    public List<Usuario> getUsuarios()
    {
        List<Usuario> list = new ArrayList<>();
        query = "SELECT * FROM usuario";

        try
        {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            resultado = sentencia.executeQuery();

            while (resultado.next())
            {
                Date date = resultado.getDate("DispatchDate");
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
        catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Usuario createUsuario(Usuario usuario) {
        // se crea usuario y se guarda en la db
        query = "INSERT INTO usuarios (rut,nombre,direccion,email,telefono,contrasena,fechaCreacion) VALUES(?,?,?,?,?,?,now())";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setString(1,usuario.getRut());
            sentencia.setString(2,usuario.getNombre());
            sentencia.setString(3,usuario.getDireccion());
            sentencia.setString(4,usuario.getEmail());
            sentencia.setString(5,usuario.getEmail());
            sentencia.setString(6,usuario.getTelefono());

            resultado = sentencia.executeQuery();
            System.out.println(resultado);
        }catch (Exception e){
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public void deleteUsuario(String id) {
        //se borra usuario de la db
    }
}

