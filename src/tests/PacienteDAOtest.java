package tests;

import DAO.FichaMedicaDAOImpl;
import DAO.UsuarioDAOImpl;
import core.DataBase;
import models.FichaMedica;
import models.Paciente;
import models.PersonalMedico;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

public class PacienteDAOtest {

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultado2;
    public String query;
    private FichaMedicaDAOImpl fichaMedicaDAO = new FichaMedicaDAOImpl();

    /*@org.junit.jupiter.api.Test
    void getPaciente() {
        int id=13;
        query = "SELECT * FROM sistema_registro_renal.paciente INNER JOIN usuario where paciente.idUsuario=?";
        Paciente paciente=null;
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,id);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Paciente dato = new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("contrasena"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime(),
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("nacionalidad"),
                        resultado.getInt("prevision"),
                        resultado.getString("telefonoAlternativo"),
                        resultado.getString("emailAlternativo")
                );
                paciente=dato;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(paciente!=null){
            System.out.println(paciente.getNombre() + " " + paciente.getRut());
        }
        //return paciente;
    }*/

    /*@org.junit.jupiter.api.Test
    void getPacientes() {
        List<Paciente> list = new ArrayList<>();
        query = "SELECT * FROM paciente inner join usuario where paciente.idUsuario=usuario.id";
        try
        {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Paciente dato = new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime(),
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("nacionalidad"),
                        resultado.getInt("prevision"),
                        resultado.getString("telefonoAlternativo"),
                        resultado.getString("emailAlternativo")
                );
                list.add(dato);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Información del personal medico: \n");
        for (Paciente paciente : list) {
            System.out.println(paciente.getNombre() + " " + paciente.getRut());
        }

       // return list;
    }*/
    @org.junit.jupiter.api.Test
    void getPacientes() {
        List<Paciente> list = new ArrayList<>();
        query = "SELECT * FROM paciente inner join usuario where paciente.idUsuario=usuario.id";
        Paciente pacienteRetorno=null;
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Paciente dato = new Paciente(
                        resultado.getInt("idUsuario"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime(),
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("nacionalidad"),
                        resultado.getInt("prevision"),
                        resultado.getString("telefonoAlternativo"),
                        resultado.getString("emailAlternativo")
                );
                list.add(dato);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Información de los pacientes: \n");
        for (Paciente paciente : list) {
            if(fichaMedicaDAO.getFichaPaciente(paciente.getRut()) != null){
              out.print("tiene ficha");
              FichaMedica fichaMedica= fichaMedicaDAO.getFichaPaciente(paciente.getRut());
              paciente.setFichaPaciente(fichaMedica);
                paciente.showUserData();
            }
        }
        //return list;
    }


    @org.junit.jupiter.api.Test
    void createPaciente() {
        Paciente paciente = new Paciente(10,"borrar","JOSE VERGARA","BRASIL 58","ASD@MAIL.COM","82017717",
                LocalDateTime.now(), Date.from(Instant.now()),"Chileno",1,"NONE","NONE");

        /**
         * Se crea usuario y se guarda en la db
         * Se retorna el objeto usuario si se pudo guardar satisfactoriamente
         * Se retorna null si hubo un error al guardar el usuario
         */
        query = "INSERT INTO usuario (rut,nombre,direccion,email,telefono,contrasena,fechaCreacion) VALUES (?,?,?,?,?,null,now())";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setString(1,paciente.getRut());
            sentencia.setString(2,paciente.getNombre());
            sentencia.setString(3,paciente.getDireccion());
            sentencia.setString(4,paciente.getEmail());
            sentencia.setString(5,paciente.getTelefono());

            resultado2 = sentencia.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultado2>0){
            out.println("Usuario: " + paciente.getNombre() + " creado satisfactoriamente!");
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
                query = "INSERT INTO paciente (idUsuario,fechaNacimiento,emailAlternativo,telefonoAlternativo,prevision,nacionalidad) VALUES (?,?,?,?,?,?)";
                conexion = DataBase.conectar();
                sentencia = conexion.prepareStatement(query);
                sentencia.setInt(1,resultado2);
                sentencia.setDate(2, java.sql.Date.valueOf(paciente.parseFechaNacimiento(paciente.getFechaNacimiento())));
                sentencia.setString(3,paciente.getEmailAlternativo());
                sentencia.setString(4,paciente.getTelefonoAlternativo());
                sentencia.setInt(5,paciente.getPrevision().getValor());
                sentencia.setString(6,paciente.getNacionalidad());

                sentencia.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }
            out.println("Exito al crear el personal: " + paciente.getNombre() + "...\n");
            //return paciente;
        }else{
            out.println("Lo sentimos, hubo un error al crear el personal: " + paciente.getNombre() + "...");
            //return null;
        }
    }

    @org.junit.jupiter.api.Test
    void updatePaciente()
    {
        Paciente paciente = new Paciente(2,"19940860-7","FELIPE GUAJARDO NUNEZ","BRASIL 58","AGN@MAIL.CL","82017717",
                LocalDateTime.now(), Date.from(Instant.now()),"Chileno",1,"telefono","mailalternativotest");

        /**
         * Se crea usuario y se guarda en la db
         * Se retorna el objeto usuario si se pudo guardar satisfactoriamente
         * Se retorna null si hubo un error al guardar el usuario
         */
        query = "SELECT * FROM paciente AS P INNER JOIN usuario AS U WHERE P.idUsuario=U.id and P.idUsuario=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,paciente.getId());
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Paciente dato = new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime(),
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("nacionalidad"),
                        resultado.getInt("prevision"),
                        resultado.getString("telefonoAlternativo"),
                        resultado.getString("emailAlternativo")
                );
                out.print("Información del usuario obtenido: \n");
                dato.showUserData();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        query = "UPDATE paciente AS P " +
                "INNER JOIN usuario AS U " +
                "ON P.idUsuario = U.id AND P.idUsuario=? " +
                "SET " +
                "rut = ? ," +
                "nombre = ? ," +
                "direccion = ? ," +
                "email = ? ," +
                "telefono = ? ," +
                "nacionalidad = ? ," +
                "prevision = ? ," +
                "telefonoAlternativo = ? ," +
                "emailAlternativo = ?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,paciente.getId());
            sentencia.setString(2,paciente.getRut());
            sentencia.setString(3,paciente.getNombre());
            sentencia.setString(4,paciente.getDireccion());
            sentencia.setString(5,paciente.getEmail());
            sentencia.setString(6,paciente.getTelefono());
            sentencia.setString(7,paciente.getNacionalidad());
            sentencia.setInt(8,paciente.getPrevision().getValor());
            sentencia.setString(9,paciente.getTelefonoAlternativo());
            sentencia.setString(10,paciente.getEmailAlternativo());
            resultado2 = sentencia.executeUpdate();
            if(resultado2 >0){
                out.println("Paciente actualizado con éxito!\n");
            }else{
                out.println("Lo sentimos, hubo un error al actualizar el Paciente...\n");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        query = "SELECT * FROM paciente AS P INNER JOIN usuario AS U WHERE P.idUsuario=U.id AND P.idUsuario=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,paciente.getId());
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Paciente dato = new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime(),
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("nacionalidad"),
                        resultado.getInt("prevision"),
                        resultado.getString("telefonoAlternativo"),
                        resultado.getString("emailAlternativo")
                );
                out.print("Información del Paciente actualizada: \n");
                dato.showUserData();
                //return dato;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //return null;
    }

    @org.junit.jupiter.api.Test
    void deletePaciente() {
        int id=2;
        UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();
        if(usuarioDAO.deleteUsuario(id)){
            out.println("*** El paciente ha sido borrado de la base de datos correctamente ***");
        }else{
            out.println("*** A ocurrido un problema al borrar el registro de la base de datos ***");

        }
        /*query = "DELETE p FROM paciente AS P JOIN usuario AS U ON P.idUsuario=U.id WHERE P.id=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,id);
            resultado2 = sentencia.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultado2 >0){
            out.println("*** El paciente ha sido borrado de la base de datos correctamente ***");
            //return true;
        }else {
            out.println("*** A ocurrido un problema al borrar el registro de la base de datos ***");
            //return false;
        }*/
    }

}
