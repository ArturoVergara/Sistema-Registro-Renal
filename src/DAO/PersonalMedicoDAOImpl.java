package DAO;

import core.DataBase;
import models.PersonalMedico;
import models.Usuario;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

public class PersonalMedicoDAOImpl implements PersonalMedicoDAO{

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultadoParaEnteros;
    public String query;
    private final UsuarioDAOImpl usuarioDAO= new UsuarioDAOImpl();

    @Override
    public PersonalMedico getPersonalMedico(String rut) {
        query = "SELECT * FROM usuario AS U INNER JOIN personal AS P WHERE rut=? AND U.id = P.idUsuario";
        PersonalMedico personalMedicoRetorno = null;
        try
        {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,rut);
            resultado = sentencia.executeQuery();

            while (resultado.next())
            {
                //Date date = resultado.getDate("fechaCreacion"); // se obtiene la fecha-hora de la db
                //Timestamp timestamp = new Timestamp(Date.from(Instant.now()));      // pasamos la fecha-hora a un formato en java
                PersonalMedico dato = new PersonalMedico(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        //timestamp.toLocalDateTime(),
                        LocalDateTime.now(),
                        resultado.getInt("tipoPersonal")
                        );
                personalMedicoRetorno = dato;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return personalMedicoRetorno;
    }

    @Override
    public PersonalMedico getPersonalMedico(PersonalMedico personalMedico) {
        query = "SELECT * FROM usuario AS U INNER JOIN personal AS P WHERE rut=? AND U.id = P.idUsuario";
        PersonalMedico personalMedicoRetorno = null;
        try
        {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,personalMedico.getRut());
            resultado = sentencia.executeQuery();

            while (resultado.next())
            {
                //Date date = resultado.getDate("fechaCreacion"); // se obtiene la fecha-hora de la db
                //Timestamp timestamp = new Timestamp(date.getTime());      // pasamos la fecha-hora a un formato en java
                PersonalMedico dato = new PersonalMedico(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        //timestamp.toLocalDateTime(),
                        LocalDateTime.now(),
                        resultado.getInt("tipoPersonal")
                );
                personalMedicoRetorno = dato;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return personalMedicoRetorno;
    }

    @Override
    public PersonalMedico getPersonalMedico(int id) {
        query = "SELECT * FROM usuario AS U INNER JOIN personal AS P WHERE P.id=? AND U.id = P.idUsuario";
        PersonalMedico personalMedicoRetorno = null;
        try
        {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,id);
            resultado = sentencia.executeQuery();

            while (resultado.next())
            {
                //Date date = resultado.getDate("fechaCreacion"); // se obtiene la fecha-hora de la db
                //Timestamp timestamp = new Timestamp(date.getTime());      // pasamos la fecha-hora a un formato en java
                PersonalMedico dato = new PersonalMedico(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        //timestamp.toLocalDateTime(),
                        LocalDateTime.now(),
                        resultado.getInt("tipoPersonal")
                );
                personalMedicoRetorno = dato;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return personalMedicoRetorno;
    }

    @Override
    public List<PersonalMedico> getAllPersonalMedico() {
        List<PersonalMedico> list = new ArrayList<>();
        query = "SELECT * FROM personal inner join usuario WHERE personal.idUsuario=usuario.id";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                //Date date = resultado.getDate("fechaCreacion");
                //Timestamp timestamp = new Timestamp(date.getTime());
                PersonalMedico dato = new PersonalMedico(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        //timestamp.toLocalDateTime(),
                        LocalDateTime.now(),
                        resultado.getInt("tipoPersonal")
                );
                list.add(dato);
            }
        }
        catch (SQLException sqlException) {
            sqlException.getErrorCode();
            sqlException.getCause();
        }
        System.out.println("Información del personal medico: \n");
        for (PersonalMedico personalMedico : list) {
            System.out.println(personalMedico.getNombre() + " " + personalMedico.getRut());
        }
        return list;
    }

    @Override
    public PersonalMedico createPersonalMedico(PersonalMedico personalMedico) {
        /**
         * Se crea usuario y se guarda en la db
         * Se retorna el objeto usuario si se pudo guardar satisfactoriamente
         * Se retorna null si hubo un error al guardar el usuario
         */
        query = "INSERT INTO usuario (rut,nombre,direccion,email,contrasena,telefono,fechaCreacion) VALUES (?,?,?,?,?,?,now())";

        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setString(1,personalMedico.getRut());
            sentencia.setString(2,personalMedico.getNombre());
            sentencia.setString(3,personalMedico.getDireccion());
            sentencia.setString(4,personalMedico.getEmail());
            sentencia.setString(5,personalMedico.getContrasena());
            sentencia.setString(6,personalMedico.getTelefono());

            resultadoParaEnteros = sentencia.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultadoParaEnteros>0){
            out.println("Usuario: " + personalMedico.getNombre() + " creado satisfactoriamente!");
            query = "SELECT (id) FROM usuario ORDER BY id DESC LIMIT 1";
            try{
                conexion = DataBase.conectar();
                sentencia = conexion.prepareStatement(query);
                resultado = sentencia.executeQuery();
                resultado.next();
                resultadoParaEnteros = resultado.getInt("id");
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                query = "INSERT INTO personal (idUsuario,tipoPersonal) VALUES (?,?)";
                conexion = DataBase.conectar();
                sentencia = conexion.prepareStatement(query);
                sentencia.setInt(1,resultadoParaEnteros);
                sentencia.setInt(2,personalMedico.getTipoPersonalInt());
                sentencia.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }
            out.println("Exito al crear el personal: " + personalMedico.getNombre() + "...\n");
            return personalMedico;
        }else{
            out.println("Lo sentimos, hubo un error al crear el personal: " + personalMedico.getNombre() + "...");
            return null;
        }
    }

    @Override
    public PersonalMedico updatePersonalMedico(PersonalMedico personalMedico) {
        /**
         * Se crea usuario y se guarda en la db
         * Se retorna el objeto usuario si se pudo guardar satisfactoriamente
         * Se retorna null si hubo un error al guardar el usuario
         */
        query = "SELECT * FROM personal AS P INNER JOIN usuario AS U WHERE P.idUsuario=U.id and P.idUsuario=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,personalMedico.getId());
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                //Date date = resultado.getDate("fechaCreacion");
                //Timestamp timestamp = new Timestamp(date.getTime());
                PersonalMedico dato = new PersonalMedico(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        //timestamp.toLocalDateTime(),
                        LocalDateTime.now(),
                        resultado.getInt("tipoPersonal")
                );
                out.print("Información del personal: \n");
                dato.showUserData();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        query = "UPDATE personal AS P " +
                "INNER JOIN usuario AS U " +
                "ON P.idUsuario = U.id AND P.idUsuario=? " +
                "SET " +
                "rut = ? ," +
                "nombre = ? ," +
                "direccion = ? ," +
                "email = ? ," +
                "telefono = ? ," +
                "tipoPersonal = ? ,";

        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,personalMedico.getId());
            sentencia.setString(2,personalMedico.getRut());
            sentencia.setString(3,personalMedico.getNombre());
            sentencia.setString(4,personalMedico.getDireccion());
            sentencia.setString(5,personalMedico.getEmail());
            sentencia.setString(6,personalMedico.getTelefono());
            sentencia.setInt(6,personalMedico.getTipoPersonalInt());

            resultadoParaEnteros = sentencia.executeUpdate();
            if(resultadoParaEnteros >0){
                out.println("Personal " + personalMedico.getTipoPersonal() + " actualizado con éxito!\n");
            }else{
                out.println("Lo sentimos, hubo un error al actualizar el Personal" + personalMedico.getTipoPersonal() + "...\n");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        query = "SELECT * FROM personal AS P INNER JOIN usuario AS U WHERE P.idUsuario=U.id AND P.idUsuario=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,personalMedico.getId());
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                //Date date = resultado.getDate("fechaCreacion");
                //Timestamp timestamp = new Timestamp(date.getTime());
                PersonalMedico dato = new PersonalMedico(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        //timestamp.toLocalDateTime(),
                        LocalDateTime.now(),
                        resultado.getInt("tipoPersonal")
                );
                out.print("Información del personal actualizada: \n");
                //dato.showUserData();
                return personalMedico;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean deletePersonalMedico(String rut) { return usuarioDAO.deleteUsuario(rut);}

    @Override
    public boolean deletePersonalMedico(PersonalMedico personalMedico){ return usuarioDAO.deleteUsuario(personalMedico.getId());}

    @Override
    public boolean deletePersonalMedico(int id){ return usuarioDAO.deleteUsuario(id);}
}
