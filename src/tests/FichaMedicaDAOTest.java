package tests;

import core.DataBase;
import models.Diagnostico;
import models.Examen;
import models.FichaMedica;
import models.Paciente;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

public class FichaMedicaDAOTest {

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultado2;
    public String query;

    @org.junit.jupiter.api.Test
    void getFichaPaciente2() {
        String rut= "16.653.498-4";
        query = "SELECT fm.* from fichamedica AS fm inner join paciente as p on fm.idPaciente=p.id inner join usuario as u on p.idUsuario=u.id where u.rut =?";
        FichaMedica fichaMedicaRetorno= null;
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,rut);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                FichaMedica dato = new FichaMedica(
                        resultado.getInt("id"),
                        resultado.getBoolean("sexo"),
                        resultado.getFloat("peso"),
                        resultado.getFloat("altura"),
                        resultado.getInt("etnia"),
                        timestamp.toLocalDateTime()
                );
                fichaMedicaRetorno=dato;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(fichaMedicaRetorno!=null){
            fichaMedicaRetorno.showFichaData();
        }
        //return fichaMedicaRetorno;
    }

    @org.junit.jupiter.api.Test
    void getFichaPaciente() {
        int id=1;
        query = "SELECT * FROM fichamedica AS FM WHERE FM.id=?";
        FichaMedica fichaMedicaRetorno= null;
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,id);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                FichaMedica dato = new FichaMedica(
                        resultado.getInt("id"),
                        resultado.getBoolean("sexo"),
                        resultado.getFloat("peso"),
                        resultado.getFloat("altura"),
                        resultado.getInt("etnia"),
                        timestamp.toLocalDateTime()
                );
                fichaMedicaRetorno=dato;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assert fichaMedicaRetorno != null;
        fichaMedicaRetorno.showFichaData();
        //return fichaMedicaRetorno;
    }

    @org.junit.jupiter.api.Test
    void getExamenesPaciente() {
        query = "SELECT * FROM EXAMEN as E JOIN fichamedica as FM ON E.idFicha=1 WHERE E.idFicha = FM.id";
        List<Examen> lista = new ArrayList<>();
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            //sentencia.setInt(1,fichaMedica.getId());
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaEmision");
                Timestamp timestamp = new Timestamp(date.getTime());

                Examen dato = new Examen(
                        resultado.getInt("id"),
                        timestamp.toLocalDateTime(),
                        resultado.getInt("tipo"),
                        resultado.getFloat("valor")
                );
                lista.add(dato);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (Examen examen : lista) {
            System.out.println(examen.getTipoExamenInt() + " " + examen.getFechaEmision());
        }
        //return lista;
    }

    @org.junit.jupiter.api.Test
    void getDiagnosticosPaciente() {
        query = "SELECT * FROM diagnostico as D JOIN fichamedica as FM ON D.idFicha=1 WHERE D.idFicha = FM.id";
        List<Diagnostico> lista = new ArrayList<>();
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            //sentencia.setInt(1,fichaMedica.getId());
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaActualizacion");
                Timestamp timestamp = new Timestamp(date.getTime());


                Diagnostico dato = new Diagnostico(
                        resultado.getInt("id"),
                        timestamp.toLocalDateTime(),
                        resultado.getFloat("resultado"),
                        resultado.getString("descripcion"),
                        resultado.getInt("categoriaDanio")
                );
                lista.add(dato);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (Diagnostico diagnostico : lista) {
            diagnostico.showDiagnosticoData();
        }
    }

    @org.junit.jupiter.api.Test
    void getUltimoDiagnostico() {
        FichaMedica fichaMedica = new FichaMedica(true,10,169,1);

        query = "SELECT * FROM diagnostico AS D INNER JOIN fichamedica AS FM ON D.idFicha=1 ORDER BY fechaActualizacion DESC LIMIT 1;";
        Diagnostico diagnostico = null;
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            //sentencia.setInt(1,fichaMedica.getId());
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Date date = resultado.getDate("fechaActualizacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Diagnostico dato = new Diagnostico(
                        resultado.getInt("id"),
                        timestamp.toLocalDateTime(),
                        resultado.getFloat("resultado"),
                        resultado.getString("descripcion"),
                        resultado.getInt("categoriaDanio")
                );
                diagnostico= dato;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (diagnostico!=null){
            diagnostico.showDiagnosticoData();
            //return diagnostico;
        }
    }

    @org.junit.jupiter.api.Test
    void agregarFichaAPaciente() {
        FichaMedica fichaMedica = new FichaMedica(true,10,10,1);
        Paciente paciente = new Paciente(2,"19940860-7","FELIPE GUAJARDO NUNEZ","BRASIL 58","AGN@MAIL.CL","82017717",
                LocalDateTime.now(), Date.from(Instant.now()),"Chileno",1,"telefono","mailalternativotest");
        query = "SELECT paciente.id FROM paciente inner join usuario ON paciente.idUsuario=usuario.id WHERE usuario.rut=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,paciente.getRut());
            resultado= sentencia.executeQuery();
            resultado.next();
            resultado2=resultado.getInt("id");
        }catch (Exception e){
            e.printStackTrace();
        }
        out.print(resultado2);

        /**
         * Se crea usuario y se guarda en la db
         * Se retorna el objeto usuario si se pudo guardar satisfactoriamente
         * Se retorna null si hubo un error al guardar el usuario
         */
        query = "INSERT INTO fichamedica (idPaciente,sexo,peso,altura,etnia,fechaCreacion) VALUES (?,?,?,?,?,now())";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,resultado2);
            sentencia.setInt(2,fichaMedica.getSexo());
            sentencia.setFloat(3,fichaMedica.getPesoPaciente());
            sentencia.setFloat(4,fichaMedica.getAlturaPaciente());
            sentencia.setInt(5,fichaMedica.getEtniaPaciente());
            resultado2 = sentencia.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultado2>0) {
            out.println("Ficha medica creada satisfactoriamente!");
            //return fichaMedica;
        }else{
            out.println("Error al crear la Ficha medica!");
            //return null;
        }
    }

    @org.junit.jupiter.api.Test
    void updateFichaPaciente() {
        FichaMedica fichaMedica = new FichaMedica(false,1000,1000,10);
        Paciente paciente = new Paciente(8,"borrar","JOSE VERGARA","BRASIL 58","ASD@MAIL.COM","82017717",
                LocalDateTime.now(), Date.from(Instant.now()),"Chileno",1,"NONE","NONE");
       query = "select p.id from fichamedica as fm join paciente as p on fm.idPaciente=p.id join usuario as u on p.idUsuario=u.id where u.rut=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,paciente.getRut());
            resultado= sentencia.executeQuery();
            resultado.next();
            resultado2=resultado.getInt("id");
        }catch (Exception e){
            e.printStackTrace();
        }
        out.print(resultado2);

        query = "UPDATE fichamedica AS FM " +
                "INNER JOIN paciente as P " +
                "ON FM.idPaciente=P.id AND P.id=? " +
                "SET sexo=?,peso=?,altura=?,etnia=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,resultado2);
            sentencia.setInt(2,fichaMedica.getSexo());
            sentencia.setFloat(3,fichaMedica.getPesoPaciente());
            sentencia.setFloat(4,fichaMedica.getAlturaPaciente());
            sentencia.setInt(5,fichaMedica.getEtniaPaciente());
            resultado2 = sentencia.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        if(resultado2 >0){
            out.println("Ficha Medica del paciente actualizada con éxito!\n");
            //return fichaMedica;
        }else{
            out.println("Lo sentimos, hubo un error al actualizar la Ficha medica...\n");
        //    return null;
        }
    }

    @org.junit.jupiter.api.Test
    void deleteFichaPaciente() {
        query = "select p.id from fichamedica as fm join paciente as p on fm.idPaciente=p.id join usuario as u on p.idUsuario=u.id where u.rut='14.501.741-6'";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            //sentencia.setString(1,paciente.getRut());
            resultado= sentencia.executeQuery();
            resultado.next();
            resultado2=resultado.getInt("id");
        }catch (Exception e){
            e.printStackTrace();
        }

        query = "DELETE FM FROM fichamedica AS FM INNER JOIN paciente AS P ON FM.idPaciente=P.id WHERE FM.idPaciente=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,resultado2);
            resultado2 = sentencia.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultado2 >0){
            out.println("*** La ficha medica ha sido borrada de la base de datos correctamente ***");
            //return true;
        }else {
            out.println("*** A ocurrido un problema al borrar el registro de la base de datos ***");
            //return false;
        }

    }

    /*@org.junit.jupiter.api.Test
    void createFichaPaciente() {

        Paciente paciente =null;
        query = "SELECT * FROM paciente AS P INNER JOIN usuario as U WHERE P.idUsuario=1";
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
                paciente=dato;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        query = "INSERT INTO fichamedica (idPaciente,sexo,peso,altura,etnia,fechaCreacion) VALUES (?,?,?,?,?,now())";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,paciente.getId());
            sentencia.setInt(2,fichaMedica.getSexo());
            sentencia.setFloat(3,fichaMedica.getPesoPaciente());
            sentencia.setFloat(4,fichaMedica.getAlturaPaciente());
            sentencia.setInt(5,fichaMedica.getEtniaPaciente());
            resultado2 = sentencia.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultado2>0) {
            out.println("Ficha medica creada satisfactoriamente!");
            fichaMedica.showFichaData();
            //return fichaMedica;
        }else{
            out.println("Error al crear la Ficha medica!");
            //return null;
        }
    }*/

    @org.junit.jupiter.api.Test
    void getFichaPacienteBoolean() {
        query = "SELECT fm.* from fichamedica AS fm inner join paciente as p on fm.idPaciente=p.id inner join usuario as u on p.idUsuario=u.id where u.rut ='16.653.498-4'";
        FichaMedica fichaMedicaRetorno= null;
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            //sentencia.setString(1,rut);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                FichaMedica dato = new FichaMedica(
                        resultado.getInt("id"),
                        resultado.getBoolean("sexo"),
                        resultado.getFloat("peso"),
                        resultado.getFloat("altura"),
                        resultado.getInt("etnia"),
                        timestamp.toLocalDateTime()
                );
                fichaMedicaRetorno=dato;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        out.print(fichaMedicaRetorno);
        //return fichaMedicaRetorno != null;
    }
}
