package DAO;

import core.DataBase;
import models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

public class UsuarioDAOImpl implements UsuarioDAO {

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultadoParaEnteros;
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
                Date date = resultado.getDate("fechaCreacion"); // se obtiene la fecha-hora de la db
                Timestamp timestamp = new Timestamp(date.getTime());      // pasamos la fecha-hora a un formato en java
                Usuario dato = new Usuario(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
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
        query = "SELECT * FROM usuario WHERE id=?";
        Usuario usuarioRetorno = null;
        try
        {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,id);
            resultado = sentencia.executeQuery();

            while (resultado.next())
            {
                Date date = resultado.getDate("fechaCreacion"); // se obtiene la fecha-hora de la db
                Timestamp timestamp = new Timestamp(date.getTime());      // pasamos la fecha-hora a un formato en java
                Usuario dato = new Usuario(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
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
    public List<Usuario> getUsuarios() {
        List<Usuario> list = new ArrayList<>();
        query = "SELECT * FROM usuario";
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
        for (Usuario usuario : list) {
            System.out.println(usuario.getNombre() + " " + usuario.getRut());
        }
        return list;
    }

    @Override
    public Usuario createUsuario(Usuario usuario) {
        /**
         * Se crea usuario y se guarda en la db
         * Se retorna el objeto usuario si se pudo guardar satisfactoriamente
         * Se retorna null si hubo un error al guardar el usuario
          */
        query = "INSERT INTO usuario (rut,nombre,direccion,email,telefono,contrasena,fechaCreacion) VALUES (?,?,?,?,?,null,now())";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setString(1,usuario.getRut());
            sentencia.setString(2,usuario.getNombre());
            sentencia.setString(3,usuario.getDireccion());
            sentencia.setString(4,usuario.getEmail());
            sentencia.setString(5,usuario.getTelefono());

            resultadoParaEnteros = sentencia.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultadoParaEnteros>0){
            out.println("Usuario: " + usuario.getNombre() + " creado satisfactoriamente!");
            return usuario;
        }else{
            out.println("Hubo un error al crear el Usuario: " + usuario.getNombre());
            return null;
        }
    }

    @Override
    public void deleteUsuario(String rut) {
        //se borra usuario de la db
        query = "DELETE FROM usuario WHERE usuario.rut=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,rut);

            resultadoParaEnteros = sentencia.executeUpdate();
            if(resultadoParaEnteros >0){
                out.println("*** El o los usuarios han sido borrado(s) de la base de datos correctamente ***");
            }else {
                out.println("*** A ocurrido un problema al borrar el registro de la base de datos ***");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean testCredentialsPersonal(String rut, String contrasena){
        query = "SELECT count(*) FROM usuario AS U inner join personal AS P where (rut=? and contrasena=?) and (contrasena IS NOT null) and P.idUsuario=U.id";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,rut);
            sentencia.setString(2,contrasena);
            resultado = sentencia.executeQuery();
            resultado.next();
            resultadoParaEnteros=resultado.getInt("count(*)");
            if(resultadoParaEnteros >0){
                out.println("*** Credenciales correctas ***");
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        out.println("*** Credenciales incorrectas ***");
        return false;
    }

    @Override
    public void cambiarContrasena(Usuario usuario){
        query = "UPDATE usuario AS U SET U.contrasena=? WHERE U.id=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,usuario.getContrasena());
            sentencia.setInt(2,usuario.getId());
            resultadoParaEnteros = sentencia.executeUpdate();

            if(resultadoParaEnteros >0){
                out.println("Clave actualizada con éxito!\n");
            }else{
                out.println("Lo sentimos, hubo un error al cambiar la clave...\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void crearContrasena(Usuario usuario){
        query = "UPDATE usuario AS U SET U.contrasena=? WHERE U.id=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,usuario.getContrasena());
            sentencia.setInt(2,usuario.getId());
            resultadoParaEnteros = sentencia.executeUpdate();

            if(resultadoParaEnteros >0){
                out.println("Clave creada con éxito!\n");
            }else{
                out.println("Lo sentimos, hubo un error al crear la clave...\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}

