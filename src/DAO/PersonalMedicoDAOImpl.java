package DAO;

import core.DataBase;
import models.PersonalMedico;
import models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class PersonalMedicoDAOImpl implements PersonalMedicoDAO{

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultadoParaEnteros;
    public String query;

    @Override
    public PersonalMedico getPersonalMedico(String rut) {
        query = "SELECT * FROM usuario inner join personal WHERE rut=?";
        Usuario usuarioRetorno = null;
        try
        {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,rut);
            resultado = sentencia.executeQuery();

            while (resultado.next())
            {
                Date date = resultado.getDate("fechaCreacion"); // se obtiene la fecha-hora de la db
                Timestamp timestamp = new Timestamp(date.getTime());      // pasamos la fecha-hora a un formato en java
                Usuario dato = new Usuario(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("contrasena"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime()
                );
                usuarioRetorno = dato;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return usuarioRetorno;

        return null;
    }

    @Override
    public List<PersonalMedico> getPersonalMedico() {
        return null;
    }

    @Override
    public PersonalMedico createPersonalMedico(PersonalMedico personalMedico) {
        return null;
    }

    @Override
    public void deletePersonalMedico(String rut) {

    }
}
