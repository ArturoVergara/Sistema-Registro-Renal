package tests;

import core.DataBase;
import models.PersonalMedico;
import models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

public class PersonalMedicoDAOTest {

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultado2;
    public String query;

    @org.junit.jupiter.api.Test
    void getPersonalMedico() {
        String rut = "111";
        query = "SELECT * FROM usuario inner join personal WHERE rut=?";
        PersonalMedico personalMedicoRetorno = null;
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1, rut);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion"); // se obtiene la fecha-hora de la db
                Timestamp timestamp = new Timestamp(date.getTime());      // pasamos la fecha-hora a un formato en java
                PersonalMedico dato = new PersonalMedico(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("contrasena"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime()
                );
                personalMedicoRetorno = dato;
                out.println(dato.getNombre() + dato.getDireccion());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void getAllPersonalMedico() {
        List<PersonalMedico> list = new ArrayList<>();
        query = "SELECT * FROM personal inner join usuario WHERE personal.idUsuario=usuario.id";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                PersonalMedico dato = new PersonalMedico(
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
        catch (SQLException sqlException) {
            sqlException.getErrorCode();
            sqlException.getCause();
        }
        for (PersonalMedico personalMedico : list) {
            System.out.println(personalMedico.getNombre() + " " + personalMedico.getRut());
        }
        //return list;
    }
}
