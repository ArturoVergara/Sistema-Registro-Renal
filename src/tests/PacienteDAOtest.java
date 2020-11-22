package tests;

import core.DataBase;
import models.Paciente;
import models.PersonalMedico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
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

    @org.junit.jupiter.api.Test
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
    }


    @org.junit.jupiter.api.Test
    void createPaciente() {
        Paciente paciente = new Paciente(30,"1111234asd","22222345asd","333334","44445","555556","66667",
                LocalDateTime.now(), Date.from(Instant.now()),"nacionalidad",1,"telefono","mailalternativotest");

        /**
         * Se crea usuario y se guarda en la db
         * Se retorna el objeto usuario si se pudo guardar satisfactoriamente
         * Se retorna null si hubo un error al guardar el usuario
         */
        query = "INSERT INTO usuario (rut,nombre,direccion,email,telefono,contrasena,fechaCreacion) VALUES (?,?,?,?,?,?,now())";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setString(1,paciente.getRut());
            sentencia.setString(2,paciente.getNombre());
            sentencia.setString(3,paciente.getDireccion());
            sentencia.setString(4,paciente.getEmail());
            sentencia.setString(5,paciente.getContrasena());
            sentencia.setString(6,paciente.getTelefono());

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
                query = "INSERT INTO paciente (idUsuario,fechaNacimiento,emailAlternativo,telefonoAlternativo,prevision,nacionalidad) VALUES (?,now(),?,?,?,?)";
                conexion = DataBase.conectar();
                sentencia = conexion.prepareStatement(query);
                sentencia.setInt(1,resultado2);
                sentencia.setString(2,paciente.getEmailAlternativo());
                sentencia.setString(3,paciente.getTelefonoAlternativo());
                sentencia.setInt(4,paciente.getPrevisionPaciente());
                sentencia.setString(5,paciente.getNacionalidad());

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
}
