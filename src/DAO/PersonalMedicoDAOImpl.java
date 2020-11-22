package DAO;

import core.DataBase;
import models.PersonalMedico;
import models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

public class PersonalMedicoDAOImpl implements PersonalMedicoDAO{

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultadoParaEnteros;
    public String query;
    public String query2;

    @Override
    public PersonalMedico getPersonalMedico(String rut) {
        query = "SELECT * FROM usuario inner join personal WHERE rut=?";
        PersonalMedico personalMedicoRetorno = null;
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
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return personalMedicoRetorno;
    }

    @Override
    public List<PersonalMedico> getPersonalMedico() {
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
        System.out.println("Información del personal medico: \n");
        for (PersonalMedico personalMedico : list) {
            System.out.println(personalMedico.getNombre() + " " + personalMedico.getRut());
        }
        return list;
    }

    @Override
    public PersonalMedico createPersonalMedico(PersonalMedico personalMedico) {
        /**
         * Se crea usuario y se guarda en la db
         * Se retorna el objeto usuario si se pudo guardar satisfactoriamente
         * Se retorna null si hubo un error al guardar el usuario
         */
        query = "INSERT INTO usuario (rut,nombre,direccion,email,telefono,contrasena,fechaCreacion) VALUES (?,?,?,?,?,?,now())";
        query2 = "INSERT INTO personal (tipoPersonal) VALUES (?)";

        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setString(1,personalMedico.getRut());
            sentencia.setString(2,personalMedico.getNombre());
            sentencia.setString(3,personalMedico.getDireccion());
            sentencia.setString(4,personalMedico.getEmail());
            sentencia.setString(5,personalMedico.getContrasena());
            sentencia.setString(6,personalMedico.getTelefono());

            resultadoParaEnteros = sentencia.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultadoParaEnteros>0){
            out.println("Personal: " + personalMedico.getNombre() + " creado satisfactoriamente!");
            try{
                conexion = DataBase.conectar();
                sentencia = conexion.prepareStatement(query2);

                sentencia.setInt(1,personalMedico.getTipoPersonalInt());
                resultadoParaEnteros = sentencia.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }

            return personalMedico;
        }else{
            out.println("Hubo un error al crear el Usuario: " + personalMedico.getNombre());
            return null;
        }
    }

    @Override
    public void deletePersonalMedico(String rut) {

    }
}
