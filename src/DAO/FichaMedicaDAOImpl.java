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
        query = "SELECT * FROM fichamedica AS FM WHERE FM.rut=?";
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
                        resultado.getString("etnia"),
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
    public FichaMedica getFichaPaciente(int id) {
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
                        resultado.getString("etnia"),
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
        query = "SELECT * FROM EXAMEN as E" +
                "JOIN fichamedica as FM" +
                "ON E.idFicha = FM.id" +
                "WHERE E.idFicha=?";
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
                        resultado.getInt("tipoExamen"),
                        resultado.getFloat("resultadoExamen")
                );
                lista.add(dato);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Diagnostico> getDiagnosticosPaciente(FichaMedica fichaMedica) {
        query = "SELECT * FROM diagnostico as D" +
                "JOIN fichamedica as FM" +
                "ON D.idFicha = FM.id" +
                "WHERE D.idFicha=?;";
        List<Diagnostico> lista = new ArrayList<>();
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,fichaMedica.getId());
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());

                Date date2 = resultado.getDate("fechaActualizacion");
                Timestamp timestamp2 = new Timestamp(date2.getTime());


                Diagnostico dato = new Diagnostico(
                        resultado.getInt("id"),
                        timestamp.toLocalDateTime(),
                        timestamp2.toLocalDateTime(),
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
        return lista;
    }

    @Override
    public List<Diagnostico> getUltimoDiagnostico(FichaMedica fichaMedica) {
        return null;
    }

    @Override
    public FichaMedica createFichaPaciente(FichaMedica fichaMedica) {
        /**
         * Se crea usuario y se guarda en la db
         * Se retorna el objeto usuario si se pudo guardar satisfactoriamente
         * Se retorna null si hubo un error al guardar el usuario
         */
        query = "INSERT INTO fichamedica (sexo,peso,altura,etnia,fechaCreacion) VALUES (?,?,?,?,now())";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setInt(1,fichaMedica.getSexo());
            sentencia.setFloat(2,fichaMedica.getPesoPaciente());
            sentencia.setFloat(3,fichaMedica.getAlturaPaciente());
            sentencia.setString(4,fichaMedica.getEtniaPaciente());

            resultadoParaEnteros = sentencia.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultadoParaEnteros>0) {
            out.println("Ficha medica creada satisfactoriamente!");
            return fichaMedica;
        }else{
            out.println("Error al crear la Ficha medica!");
            return null;
        }
    }

    @Override
    public FichaMedica updateFichaPaciente(FichaMedica fichaMedica) {
        /*query = "SELECT * FROM fichamedica AS FM WHERE FM.id=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,fichaMedica.getId());
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                FichaMedica dato = new FichaMedica(
                        resultado.getInt("id"),
                        resultado.getBoolean("sexo"),
                        resultado.getFloat("peso"),
                        resultado.getFloat("altura"),
                        resultado.getString("etnia"),
                        timestamp.toLocalDateTime()
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
        query = "UPDATE fichamedica AS FM" +
                "SET " +
                "sexo = ? ," +
                "peso = ? ," +
                "altura = ? ," +
                "etnia = ? ," +
                "WHERE FM.id=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,fichaMedica.getSexo());
            sentencia.setFloat(2,fichaMedica.getPesoPaciente());
            sentencia.setFloat(3,fichaMedica.getAlturaPaciente());
            sentencia.setString(4,fichaMedica.getEtniaPaciente());
            sentencia.setInt(5,fichaMedica.getId());
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
        query = "DELETE f FROM fichamedica AS F INNER JOIN paciente AS P WHERE F.idUsuario=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,paciente.getId());
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
