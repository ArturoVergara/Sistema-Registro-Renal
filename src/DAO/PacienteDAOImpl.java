package DAO;

import core.DataBase;
import models.Paciente;
import models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PacienteDAOImpl implements PacienteDAO{

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    public String query;

    @Override
    public Paciente getPaciente(String id)
    {
        query = "SELECT * FROM paciente WHERE id=?";
        Paciente paciente=null;
        try
        {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            resultado = sentencia.executeQuery();

            while (resultado.next())
            {
                Date date = resultado.getDate("DispatchDate");
                Timestamp timestamp = new Timestamp(date.getTime());
                Paciente dato = new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("contrasena"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime()
                );
                paciente=dato;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return paciente;
    }

    @Override
    public List<Paciente> getPacientes() {
        List<Paciente> list = new ArrayList<>();
        query = "SELECT * FROM paciente ";

        try
        {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            resultado = sentencia.executeQuery();

            while (resultado.next())
            {
                Date date = resultado.getDate("DispatchDate");
                Timestamp timestamp = new Timestamp(date.getTime());
                Paciente dato = new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("contrasena"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime()
                );
                list.add(dato);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Paciente createPaciente(Paciente paciente) {
        return null;
    }

    @Override
    public void deletePaciente(String id) {

    }
}
