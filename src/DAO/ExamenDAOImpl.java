package DAO;

import core.DataBase;
import models.Diagnostico;
import models.Examen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

public class ExamenDAOImpl implements ExamenDAO{

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultadoParaEnteros;
    public String query;

    @Override
    public Diagnostico getExamenPaciente(String rut) {
        return null;
    }

    @Override
    public Diagnostico getExamenPaciente(int id) {
        return null;
    }

    @Override
    public List<Diagnostico> getExamenes() {
        return null;
    }

    @Override
    public Diagnostico createExamenPaciente(Examen examen) {
        return null;
    }

    @Override
    public Diagnostico updateExamenPaciente(Examen examen) {
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
