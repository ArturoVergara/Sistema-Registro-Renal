package DAO;

import core.DataBase;
import models.Diagnostico;
import models.Examen;
import models.FichaMedica;
import models.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

public class FichaMedicaDAOImpl implements FichaMedicaDAO{

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultadoParaEnteros;
    public String query;

    @Override
    public FichaMedica getFichaPaciente(String rut) {
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(fichaMedicaRetorno!=null){
            fichaMedicaRetorno.showFichaData();
            return fichaMedicaRetorno;
        }else{
            return null;
        }
    }
    @Override
    public boolean getFichaPacienteBoolean(String rut) {
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return fichaMedicaRetorno != null;
    }


    @Override
    public FichaMedica getFichaPaciente(int id){ //id -> id del paciente
        query = "SELECT fm.* from fichamedica AS fm inner join paciente as p on fm.idPaciente=?";
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
        return fichaMedicaRetorno;
    }

    @Override
    public List<Examen> getExamenesPaciente(FichaMedica fichaMedica) {
        query = "SELECT * FROM EXAMEN as E JOIN fichamedica as FM ON E.idFicha=? WHERE E.idFicha = FM.id";
        List<Examen> lista = new ArrayList<>();
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,fichaMedica.getId());
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
        if(lista.size()>0){
            for (Examen examen : lista) {
                examen.showExamenData();
            }
            return lista;
        }else{
            return null;
        }
    }

    @Override
    public List<Diagnostico> getDiagnosticosPaciente(FichaMedica fichaMedica) {
        query = "SELECT * FROM diagnostico as D JOIN fichamedica as FM ON D.idFicha=? WHERE D.idFicha = FM.id";
        List<Diagnostico> lista = new ArrayList<>();
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,fichaMedica.getId());
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
        if(lista.size()>0){
            for (Diagnostico diagnostico : lista) {
                diagnostico.showDiagnosticoData();
            }
            return lista;
        }else{
            return null;
        }
    }

    @Override
    public Diagnostico getUltimoDiagnostico(FichaMedica fichaMedica) {
        query = "SELECT * FROM diagnostico AS D INNER JOIN fichamedica AS FM ON D.idFicha=? ORDER BY fechaActualizacion DESC LIMIT 1;";
        Diagnostico diagnostico = null;
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setInt(1,fichaMedica.getId());
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
            return diagnostico;
        }else{
            return null;
        }
    }

    @Override
    public FichaMedica agregarFichaAPaciente(Paciente paciente, FichaMedica fichaMedica) {
        query = "SELECT paciente.id FROM paciente inner join usuario ON paciente.idUsuario=usuario.id WHERE usuario.rut=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,paciente.getRut());
            resultado= sentencia.executeQuery();
            resultado.next();
            resultadoParaEnteros=resultado.getInt("id");
        }catch (Exception e){
            e.printStackTrace();
        }
        //out.print(resultadoParaEnteros);
        query = "INSERT INTO fichamedica (idPaciente,sexo,peso,altura,etnia,fechaCreacion) VALUES (?,?,?,?,?,now())";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,resultadoParaEnteros);
            sentencia.setInt(2,fichaMedica.getSexo());
            sentencia.setFloat(3,fichaMedica.getPesoPaciente());
            sentencia.setFloat(4,fichaMedica.getAlturaPaciente());
            sentencia.setInt(5,fichaMedica.getEtniaPaciente());
            resultadoParaEnteros = sentencia.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultadoParaEnteros>0) {
            out.println("Ficha medica agregada al paciente " + paciente.getNombre() +" satisfactoriamente!");
            return fichaMedica;
        }else{
            out.println("Error al agregar la ficha medica al paciente!");
            return null;
        }
    }

    @Override
    public FichaMedica updateFichaPaciente(Paciente paciente, FichaMedica fichaMedica) {
        query = "select p.id from fichamedica as fm join paciente as p on fm.idPaciente=p.id join usuario as u on p.idUsuario=u.id where u.rut=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,paciente.getRut());
            resultado= sentencia.executeQuery();
            resultado.next();
            resultadoParaEnteros=resultado.getInt("id");
        }catch (Exception e){
            e.printStackTrace();
        }
        //out.print(resultadoParaEnteros);
        query = "UPDATE fichamedica AS FM " +
                "INNER JOIN paciente as P " +
                "ON FM.idPaciente=P.id AND P.id=? " +
                "SET sexo=?,peso=?,altura=?,etnia=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,resultadoParaEnteros);
            sentencia.setInt(2,fichaMedica.getSexo());
            sentencia.setFloat(3,fichaMedica.getPesoPaciente());
            sentencia.setFloat(4,fichaMedica.getAlturaPaciente());
            sentencia.setInt(5,fichaMedica.getEtniaPaciente());
            resultadoParaEnteros = sentencia.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        if(resultadoParaEnteros >0){
            out.println("Ficha Medica del paciente actualizada con éxito!\n");
            return fichaMedica;
        }else{
            out.println("Lo sentimos, hubo un error al actualizar la Ficha medica...\n");
            return null;
        }
    }

    @Override
    public Diagnostico agregarDiagnosticoAFicha(FichaMedica fichaMedica, Diagnostico diagnostico) {
        query = "select fm.id from fichamedica as fm where fm.sexo=? and fm.peso=? and fm.altura=? and fm.etnia=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            //sentencia.setString(1,paciente.getRut());
            resultado= sentencia.executeQuery();
            resultado.next();
            resultadoParaEnteros=resultado.getInt("id");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Examen agregarExamenAFicha(FichaMedica fichaMedica, Examen examen) {

        return null;
    }

    @Override
    public boolean deleteFichaPaciente(int idFicha) {
        query = "DELETE FROM fichamedica AS FM WHERE FM.id=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,idFicha);
            resultadoParaEnteros = sentencia.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultadoParaEnteros >0){
            out.println("*** La ficha medica ha sido borrado de la base de datos correctamente ***");
            return true;
        }else {
            out.println("*** A ocurrido un problema al borrar el registro de la base de datos ***");
            return false;
        }
    }

    @Override
    public boolean deleteFichaPaciente(FichaMedica fichaMedica) {
        query = "DELETE FROM fichamedica AS FM WHERE FM.id=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,fichaMedica.getId());
            resultadoParaEnteros = sentencia.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultadoParaEnteros >0){
            out.println("*** La ficha medica ha sido borrada de la base de datos correctamente ***");
            return true;
        }else {
            out.println("*** A ocurrido un problema al borrar el registro de la base de datos ***");
            return false;
        }
    }

    @Override
    public boolean deleteFichaPaciente(Paciente paciente) {
        query = "select p.id from fichamedica as fm join paciente as p on fm.idPaciente=p.id join usuario as u on p.idUsuario=u.id where u.rut=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,paciente.getRut());
            resultado= sentencia.executeQuery();
            resultado.next();
            resultadoParaEnteros=resultado.getInt("id");
        }catch (Exception e){
            e.printStackTrace();
        }

        query = "DELETE FM FROM fichamedica AS FM INNER JOIN paciente AS P ON FM.idPaciente=P.id WHERE FM.idPaciente=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,resultadoParaEnteros);
            resultadoParaEnteros = sentencia.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultadoParaEnteros >0){
            out.println("*** La ficha medica ha sido borrada de la base de datos correctamente ***");
            return true;
        }else {
            out.println("*** A ocurrido un problema al borrar el registro de la base de datos ***");
            return false;
        }

    }
}
