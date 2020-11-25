package DAO;

import core.DataBase;
import models.Diagnostico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class DiagnosticoDAOImpl implements DiagnosticoDAO {
    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultadoParaEnteros;
    public String query;

    @Override
    public Diagnostico getDiagnosticoPaciente(String rut) {
        return null;
    }

    @Override
    public Diagnostico getDiagnosticoPaciente(int id) {
        return null;
    }

    @Override
    public List<Diagnostico> getDiagnosticosPaciente() {
        return null;
    }

    @Override
    public Diagnostico createDiagnosticoPaciente(Diagnostico diagnostico) {
        return null;
    }

    @Override
    public Diagnostico updateDiagnostico(Diagnostico diagnostico) {
        return null;
    }

    @Override
    public List<Diagnostico> agregarDiagnosticoPaciente(Diagnostico diagnostico) {
        return null;
    }

    @Override
    public boolean deleteDiagnosticoPaciente(int idDiagnostico) {
        query = "DELETE FROM diagnostico where diagnostico.id=?";
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,idDiagnostico);
            resultadoParaEnteros = sentencia.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultadoParaEnteros > 0;
    }
}
