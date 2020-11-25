package DAO;

import core.DataBase;
import models.Diagnostico;
import models.Examen;
import models.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

import static java.lang.System.out;

public class ExamenDAOImpl implements ExamenDAO{

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultadoParaEnteros;
    public String query;

    @Override
    public Examen getExamenPaciente(String rut) {
        return null;
    }

    @Override
    public Examen getExamenPaciente(int id) {
        return null;
    }

    @Override
    public List<Diagnostico> getExamenes() {
        return null;
    }

    @Override
    public Examen createExamenPaciente(Paciente paciente, Examen examen) {
        /*query = "SELECT fm.id from fichamedica as fm inner join paciente as p on fm.idPaciente=p.id inner join usuario as u on p.idUsuario=u.id where u.rut=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,paciente.getRut());

            resultado = sentencia.executeQuery();
            resultado.next();
            resultadoParaEnteros = resultado.getInt("id");

        }catch (Exception e){
            e.printStackTrace();
        }

        query = "INSERT INTO examen (idFicha,fechaEmision,tipo,valor) VALUES (?,now(),?,?)";

        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setInt(1,resultadoParaEnteros);
            sentencia.setInt(2,examen.getTipoExamen().getValor());
            sentencia.setFloat(3,examen.getResultadoExamen());
            resultadoParaEnteros = sentencia.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultadoParaEnteros>0){
            out.println("Examen: ");
            examen.showExamenData();
            out.println("\ncreado satisfactoriamente!");
            return examen;
        } else {
            return null;
        }*/
        query = "INSERT INTO examen (idFicha,fechaEmision,tipo,valor) VALUES (?,now(),?,?)";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setInt(1,paciente.getFichaPaciente().getId());
            sentencia.setInt(2,examen.getTipoExamen().getValor());
            sentencia.setFloat(3,examen.getResultadoExamen());
            resultadoParaEnteros = sentencia.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultadoParaEnteros>0){
            out.println("Examen: ");
            examen.showExamenData();
            out.println("\ncreado satisfactoriamente!");
            return examen;
        } else {
            out.println("error al crear examen: ");
            return null;
        }
    }

    @Override
    public Examen updateExamenPaciente(Examen examen) {
        return null;
    }

    @Override
    public boolean deleteExamenPaciente(int idExamen) {
        /*query = "SELECT fm.* from fichamedica as fm join examen as e on e.idFicha=fm.id where e.id=?";
        FichaMedica fichaMedicaRetorno = null;
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,idExamen);
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
            List<Examen> examenList = fichaMedicaRetorno.getExamenesPaciente();
            examenList.removeIf(examen -> examen.getId() == idExamen);
        }else{
            return false;
        }*/

        query = "DELETE FROM examen where examen.id=?";
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,idExamen);
            resultadoParaEnteros = sentencia.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultadoParaEnteros > 0;
    }

}
