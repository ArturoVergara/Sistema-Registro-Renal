package DAO;

import core.DataBase;
import models.Diagnostico;
import models.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.lang.System.out;

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
    public Diagnostico agregarDiagnosticoPaciente(Paciente paciente, Diagnostico diagnostico) {
        query = "SELECT fm.id from fichamedica as fm inner join paciente as p on fm.idPaciente=p.id inner join usuario as u on p.idUsuario=u.id where u.rut=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1, paciente.getRut());

            resultado = sentencia.executeQuery();
            resultado.next();
            resultadoParaEnteros = resultado.getInt("id");

        }catch (Exception e){
            e.printStackTrace();
        }

        out.print(resultadoParaEnteros);

        query = "INSERT INTO diagnostico (idFicha,fechaActualizacion,resultado,descripcion,categoriaDanio) VALUES (?,now(),?,?,?)";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setInt(1,resultadoParaEnteros);
            sentencia.setFloat(2, diagnostico.getResultadoFiltradoGlomerular());
            sentencia.setString(3,diagnostico.getDescripcionDiagnostico());
            sentencia.setInt(3,diagnostico.getCategoriaDanioPaciente().getValor());

            resultadoParaEnteros = sentencia.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        if(resultadoParaEnteros>0){
            out.println("Diagnostico: ");
            diagnostico.showDiagnosticoData();
            out.println("\ncreado satisfactoriamente!");
            return diagnostico;
        } else {
            out.println("error al crear el Diagnostico!");
            return null;
        }
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
