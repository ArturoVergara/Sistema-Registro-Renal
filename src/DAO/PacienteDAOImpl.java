package DAO;

import core.DataBase;
import models.Paciente;
import models.PersonalMedico;
import models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

public class PacienteDAOImpl implements PacienteDAO{

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultadoParaEnteros;
    public String query;

    @Override
    public Paciente getPaciente(String rut) {
        query = "SELECT * FROM paciente AS P INNER JOIN usuario AS U WHERE U.rut=? and U.id=P.idUsuario";
        Paciente pacienteRetorno = null;
        try
        {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,rut);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Paciente dato = new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime(),
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("nacionalidad"),
                        resultado.getInt("prevision"),
                        resultado.getString("telefonoAlternativo"),
                        resultado.getString("emailAlternativo")
                );
                pacienteRetorno=dato;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return pacienteRetorno;
    }

    @Override
    public Paciente getPaciente(int id)
    {
        query = "SELECT * FROM paciente AS P INNER JOIN usuario AS U where P.idUsuario=? and P.idUsuario = U.id";
        Paciente paciente=null;
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,id);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Paciente dato = new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime(),
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("nacionalidad"),
                        resultado.getInt("prevision"),
                        resultado.getString("telefonoAlternativo"),
                        resultado.getString("emailAlternativo")
                );
                paciente=dato;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(paciente!=null){
            System.out.println(paciente.getNombre() + " " + paciente.getRut());
        }
        return paciente;
    }

    @Override
    public List<Paciente> getPacientes() {
        List<Paciente> list = new ArrayList<>();
        query = "SELECT * FROM paciente inner join usuario where paciente.idUsuario=usuario.id";
        try
        {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Paciente dato = new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime(),
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("nacionalidad"),
                        resultado.getInt("prevision"),
                        resultado.getString("telefonoAlternativo"),
                        resultado.getString("emailAlternativo")
                );
                list.add(dato);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Información de los pacientes: \n");
        for (Paciente paciente : list) {
            System.out.println(paciente.getNombre() + " " + paciente.getRut());
        }
        return list;
    }

    @Override
    public Paciente createPaciente(Paciente paciente) {
        /**
         * Se crea usuario y se guarda en la db
         * Se retorna el objeto usuario si se pudo guardar satisfactoriamente
         * Se retorna null si hubo un error al guardar el usuario
         */
        query = "INSERT INTO usuario (rut,nombre,direccion,email,contrasena,telefono,fechaCreacion) VALUES (?,?,?,?,?,?,now())";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setString(1,paciente.getRut());
            sentencia.setString(2,paciente.getNombre());
            sentencia.setString(3,paciente.getDireccion());
            sentencia.setString(4,paciente.getEmail());
            sentencia.setString(5,paciente.getContrasena());
            sentencia.setString(6,paciente.getTelefono());

            resultadoParaEnteros = sentencia.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultadoParaEnteros>0){
            out.println("Usuario: " + paciente.getNombre() + " creado satisfactoriamente!");
            query = "SELECT (id) FROM usuario ORDER BY id DESC";
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
                query = "INSERT INTO paciente (idUsuario,fechaNacimiento,emailAlternativo,telefonoAlternativo,prevision,nacionalidad) VALUES (?,now(),?,?,?,?)";
                conexion = DataBase.conectar();
                sentencia = conexion.prepareStatement(query);
                sentencia.setInt(1,resultadoParaEnteros);
                sentencia.setString(2,paciente.getEmailAlternativo());
                sentencia.setString(3,paciente.getTelefonoAlternativo());
                sentencia.setInt(4,paciente.getPrevision().getValor());
                sentencia.setString(5,paciente.getNacionalidad());

                sentencia.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }
            out.println("Exito al crear el personal: " + paciente.getNombre() + "...\n");
            paciente.setId(resultadoParaEnteros);
            return paciente;
        }else{
            out.println("Lo sentimos, hubo un error al crear el personal: " + paciente.getNombre() + "...");
            return null;
        }
    }

    @Override
    public Paciente updatePaciente(Paciente paciente) {
        /**
         * Se crea usuario y se guarda en la db
         * Se retorna el objeto usuario si se pudo guardar satisfactoriamente
         * Se retorna null si hubo un error al guardar el usuario
         */
        query = "SELECT * FROM paciente AS P INNER JOIN usuario AS U WHERE P.idUsuario=U.id and P.idUsuario=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,paciente.getId());
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Paciente dato = new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime(),
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("nacionalidad"),
                        resultado.getInt("prevision"),
                        resultado.getString("telefonoAlternativo"),
                        resultado.getString("emailAlternativo")
                );
                out.print("Información del paciente: \n");
                dato.showUserData();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        query = "UPDATE paciente AS P " +
                "INNER JOIN usuario AS U " +
                "ON P.idUsuario = U.id AND P.idUsuario=? " +
                "SET " +
                "rut = ? ," +
                "nombre = ? ," +
                "direccion = ? ," +
                "email = ? ," +
                "telefono = ? ," +
                "nacionalidad = ? ," +
                "prevision = ? ," +
                "telefonoAlternativo = ? ," +
                "emailAlternativo = ?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,paciente.getId());
            sentencia.setString(2,paciente.getRut());
            sentencia.setString(3,paciente.getNombre());
            sentencia.setString(4,paciente.getDireccion());
            sentencia.setString(5,paciente.getEmail());
            sentencia.setString(6,paciente.getTelefono());
            sentencia.setString(7,paciente.getNacionalidad());
            sentencia.setInt(8,paciente.getPrevision().getValor());
            sentencia.setString(9,paciente.getTelefonoAlternativo());
            sentencia.setString(10,paciente.getEmailAlternativo());
            resultadoParaEnteros = sentencia.executeUpdate();
            if(resultadoParaEnteros >0){
                out.println("Paciente actualizado con éxito!\n");
            }else{
                out.println("Lo sentimos, hubo un error al actualizar el Paciente...\n");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        query = "SELECT * FROM paciente AS P INNER JOIN usuario AS U WHERE P.idUsuario=U.id AND P.idUsuario=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,paciente.getId());
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Paciente dato = new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("rut"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"),
                        resultado.getString("email"),
                        resultado.getString("telefono"),
                        timestamp.toLocalDateTime(),
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("nacionalidad"),
                        resultado.getInt("prevision"),
                        resultado.getString("telefonoAlternativo"),
                        resultado.getString("emailAlternativo")
                );
                out.print("Información del paciente actualizada: \n");
                dato.showUserData();
                return dato;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deletePaciente(int id) {
        query = "DELETE p FROM paciente AS P JOIN usuario AS U ON P.idUsuario=U.id WHERE P.id=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,id);
            resultadoParaEnteros = sentencia.executeUpdate();
            if(resultadoParaEnteros >0){
                out.println("*** El paciente ha sido borrado de la base de datos correctamente ***");
            }else {
                out.println("*** A ocurrido un problema al borrar el registro de la base de datos ***");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void deletePaciente(Paciente paciente) {
        query = "DELETE p FROM paciente AS P JOIN usuario AS U ON P.idUsuario=U.id WHERE P.id=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,paciente.getId());
            resultadoParaEnteros = sentencia.executeUpdate();
            if(resultadoParaEnteros >0){
                out.println("*** El paciente ha sido borrado de la base de datos correctamente ***");
            }else {
                out.println("*** A ocurrido un problema al borrar el registro de la base de datos ***");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deletePaciente(String rut) {
        query = "DELETE p FROM paciente AS P JOIN usuario AS U ON P.idUsuario=U.id WHERE U.rut=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,rut);
            resultadoParaEnteros = sentencia.executeUpdate();
            if(resultadoParaEnteros >0){
                out.println("*** El paciente ha sido borrado de la base de datos correctamente ***");
            }else {
                out.println("*** A ocurrido un problema al borrar el registro de la base de datos ***");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
