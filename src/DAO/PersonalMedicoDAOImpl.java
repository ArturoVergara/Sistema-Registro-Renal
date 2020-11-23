package DAO;

import core.DataBase;
import models.PersonalMedico;

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

    @Override
    public PersonalMedico getPersonalMedico(String rut) {
        query = "SELECT * FROM Usuario AS U INNER JOIN Personal AS P WHERE rut=? AND U.id = P.idUsuario";
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
                        timestamp.toLocalDateTime(),
                        resultado.getInt("tipoPersonal")
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
    public List<PersonalMedico> getAllPersonalMedico() {
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
                        timestamp.toLocalDateTime(),
                        resultado.getInt("tipoPersonal")
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
            out.println("Usuario: " + personalMedico.getNombre() + " creado satisfactoriamente!");
            query = "SELECT (id) FROM usuario ORDER BY id DESC LIMIT 1";
            try{
                conexion = DataBase.conectar();
                sentencia = conexion.prepareStatement(query);
                resultado = sentencia.executeQuery();
                resultado.next();
                resultadoParaEnteros = resultado.getInt("id");
            }catch (Exception e){
                e.printStackTrace();
            }

            try{
                query = "INSERT INTO personal (idUsuario,tipoPersonal) VALUES (?,?)";
                conexion = DataBase.conectar();
                sentencia = conexion.prepareStatement(query);
                sentencia.setInt(1,resultadoParaEnteros);
                sentencia.setInt(2,personalMedico.getTipoPersonalInt());
                sentencia.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }
            out.println("Exito al crear el personal: " + personalMedico.getNombre() + "...\n");
            return personalMedico;
        }else{
            out.println("Lo sentimos, hubo un error al crear el personal: " + personalMedico.getNombre() + "...");
            return null;
        }
    }


    @Override
    public void deletePersonalMedico(String rut) {

    }
}
