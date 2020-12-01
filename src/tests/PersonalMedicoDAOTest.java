package tests;

import core.DataBase;
import models.PersonalMedico;
import models.enums.PersonalEnum;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

public class PersonalMedicoDAOTest {

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultado2;
    public String query;

    @org.junit.jupiter.api.Test
    void getPersonalMedico() {
        String rut = "111";
        query = "SELECT * FROM usuario inner join personal WHERE rut=?";
        PersonalMedico personalMedicoRetorno = null;
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1, rut);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion"); // se obtiene la fecha-hora de la db
                Timestamp timestamp = new Timestamp(date.getTime());      // pasamos la fecha-hora a un formato en java
                PersonalMedico dato = new PersonalMedico(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime(),
                        resultado.getInt("tipoPersonal")

                );
                personalMedicoRetorno = dato;
                out.println(dato.getNombre() + dato.getDireccion());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void getAllPersonalMedico() {
        List<PersonalMedico> list = new ArrayList<>();
        query = "SELECT * FROM personal inner join usuario WHERE personal.idUsuario=usuario.id";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                PersonalMedico dato = new PersonalMedico(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime(),
                        resultado.getInt("tipoPersonal")

                );
                list.add(dato);
            }
        }
        catch (SQLException sqlException) {
            sqlException.getErrorCode();
            sqlException.getCause();
        }
        for (PersonalMedico personalMedico : list) {
            System.out.println(personalMedico.getNombre() + " " + personalMedico.getRut() + " Creado en: " +  personalMedico.getFechaCreacion());
        }
        //return list;
    }

    /*@org.junit.jupiter.api.Test
    void createPersonalMedico() {
        PersonalMedico personalMedico = new PersonalMedico("11111","felipe","feko","asdas@mail.cl","kkk");

        query = "INSERT INTO usuario (rut,nombre,direccion,email,telefono,contrasena,fechaCreacion) VALUES (?,?,?,?,?,?,now())";

        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setString(1,personalMedico.getRut());
            sentencia.setString(2,personalMedico.getNombre());
            sentencia.setString(3,personalMedico.getDireccion());
            sentencia.setString(4,personalMedico.getEmail());
            sentencia.setString(5,personalMedico.getTelefono());

            resultado2 = sentencia.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultado2>0){
            out.println("Usuario: " + personalMedico.getNombre() + " creado satisfactoriamente!");
            query = "SELECT (id) FROM usuario ORDER BY id DESC LIMIT 1";
            try{
                conexion = DataBase.conectar();
                sentencia = conexion.prepareStatement(query);
                resultado = sentencia.executeQuery();
                resultado.next();
                resultado2 = resultado.getInt("id");
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                query = "INSERT INTO personal (idUsuario,tipoPersonal) VALUES (?,?)";
                conexion = DataBase.conectar();
                sentencia = conexion.prepareStatement(query);
                sentencia.setInt(1,resultado2);
                sentencia.setInt(2,personalMedico.getTipoPersonalInt());
                sentencia.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }
            out.println("Exito al crear el personal: " + personalMedico.getNombre() + "...\n");
            //return personalMedico;
        }else{
            out.println("Lo sentimos, hubo un error al crear el personal: " + personalMedico.getNombre() + "...");
            //return null;
        }
    }*/

    @org.junit.jupiter.api.Test
    void updatePersonalMedico() {
        PersonalMedico personalMedico = new PersonalMedico(3,"ASDnovo","nombrenuevo","dirnueva","mailnuevo","tele",1);
        query = "UPDATE personal AS P " +
                "INNER JOIN usuario AS U " +
                "ON P.idUsuario = U.id AND P.id=? " +
                "SET " +
                "rut = ? ," +
                "nombre = ? ," +
                "direccion = ? ," +
                "email = ? ," +
                "telefono = ? ," +
                "tipoPersonal = ?";

        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,personalMedico.getId());
            sentencia.setString(2,personalMedico.getRut());
            sentencia.setString(3,personalMedico.getNombre());
            sentencia.setString(4,personalMedico.getDireccion());
            sentencia.setString(5,personalMedico.getEmail());
            sentencia.setString(6,personalMedico.getTelefono());
            sentencia.setInt(7,personalMedico.getTipoPersonalInt());

            resultado2 = sentencia.executeUpdate();
            if(resultado2 >0){
                out.println("Personal " + personalMedico.getTipoPersonal() + " actualizado con éxito!\n");
                //personalMedico.showUserData();
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
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                PersonalMedico dato = new PersonalMedico(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime(),
                        resultado.getInt("tipoPersonal")
                );
                out.print("Información del personal actualizada: \n");
                dato.showUserData();
                //return personalMedico;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //return null;*/
    }
}
