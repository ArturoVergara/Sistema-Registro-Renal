package tests;

import core.DataBase;
import models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;


class UsuarioDAOImplTest{

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultado2;
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
        Usuario usuario = new Usuario("ppp2","ppp2","ppp2","ppp2","ppp2","ppp2");

        query = "INSERT INTO usuario (rut,nombre,direccion,email,telefono,contrasena,fechaCreacion) VALUES (?,?,?,?,?,?,now())";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setString(1,usuario.getRut());
            sentencia.setString(2,usuario.getNombre());
            sentencia.setString(3,usuario.getDireccion());
            sentencia.setString(4,usuario.getEmail());
            sentencia.setString(5,usuario.getContrasena());
            sentencia.setString(6,usuario.getTelefono());

            resultado2= sentencia.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }

        if(resultado2>0){
            out.println("Usuario: " + usuario.getNombre() + " creado satisfactoriamente!");
            //return usuario;
        }else{
            out.println("Hubo un error al crear el Usuario: " + usuario.getNombre());
        }
        //return usuario;
        //System.out.println("Nuevo usuario: "+ usuario.getNombre());
    }

    @org.junit.jupiter.api.Test
    void deleteUsuario() {
        String rut = "ppp1";
        query = "DELETE FROM usuario WHERE usuario.rut=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,rut);

            resultado2 = sentencia.executeUpdate();
            out.println(resultado2);
            if(resultado2 >0){
                out.println("*** El o los usuarios han sido borrado(s) de la base de datos correctamente ***");
            }else {
                out.println("*** A ocurrido un problema al borrar el registro de la base de datos ***");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void testCredentialsPersonal(){
        String rut = "111";
        String contrasena = "asd";
        /**
         * estas credenciales retornan un 1 si se encuentra un personal
         * 0 será retornado si no se encuentra un personal, es decir, las credenciales son erróneas.
         */
        List<Usuario> list = new ArrayList<>();
        query = "SELECT count(*) FROM sistema_registro_renal.usuario inner join personal where (rut=? and contrasena=?) and personal.idUsuario=usuario.id";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,rut);
            sentencia.setString(2,contrasena);

            resultado = sentencia.executeQuery();
            resultado.next();
            resultado2=resultado.getInt("count(*)");
            System.out.println(resultado2);
            if(resultado2>0){
                System.out.println(true);
                //return false;
            }else{
                System.out.println(false);
            }
            //return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        //return true;
    }
}