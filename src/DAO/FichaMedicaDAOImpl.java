package DAO;

import core.DataBase;
import models.Diagnostico;
import models.Examen;
import models.FichaMedica;
import models.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        /*query = "SELECT * FROM diagnostico as D" +
                "JOIN fichamedica as FM" +
                "ON D.idFicha = FM.id" +
                "WHERE D.idFicha=1";
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
        return fichaMedicaRetorno;*/
        return null;
    }

    @Override
    public List<Diagnostico> getUltimoDiagnostico(FichaMedica fichaMedica) {
        return null;
    }

    @Override
    public FichaMedica createFichaPaciente(FichaMedica fichaMedica) {
        return null;
    }

    @Override
    public FichaMedica updateFichaPaciente(FichaMedica fichaMedica) {
        return null;
    }

    @Override
    public Diagnostico agregarDiagnosticoAFicha(Diagnostico diagnostico) {
        return null;
    }

    @Override
    public Examen agregarExamenAFicha(Examen examen) {
        return null;
    }

    @Override
    public FichaMedica deleteFichaPaciente(int idFicha) {
        return null;
    }

    @Override
    public FichaMedica deleteFichaPaciente(Paciente paciente) {
        return null;
    }

    @Override
    public FichaMedica deleteFichaPaciente(String rutPaciente) {
        return null;
    }
}
