package DAO;

import core.DataBase;
import models.FichaMedica;
import models.Paciente;

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
    private final UsuarioDAOImpl usuarioDAO= new UsuarioDAOImpl();
    private final FichaMedicaDAO fichaMedicaDAO = new FichaMedicaDAOImpl();

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
                        resultado.getInt("idUsuario"),
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
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(pacienteRetorno !=null){
            FichaMedica fichaMedica= fichaMedicaDAO.getFichaPaciente(pacienteRetorno.getRut());
            if(fichaMedicaDAO.getFichaPacienteBoolean(pacienteRetorno.getRut())){
                pacienteRetorno.setFichaPaciente(fichaMedica);
                pacienteRetorno.showUserData();
                return pacienteRetorno;
            }
        }
        return pacienteRetorno;
    }

    @Override
    public Paciente getPaciente(int id)
    {
        query = "SELECT * FROM paciente AS P INNER JOIN usuario AS U where P.idUsuario=? and P.idUsuario = U.id";
        Paciente pacienteRetorno=null;
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,id);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Paciente dato = new Paciente(
                        resultado.getInt("idUsuario"),
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(pacienteRetorno !=null && fichaMedicaDAO.getFichaPaciente(pacienteRetorno.getRut()) != null){
            FichaMedica fichaMedica= fichaMedicaDAO.getFichaPaciente(pacienteRetorno.getRut());
            if(fichaMedicaDAO.getFichaPacienteBoolean(pacienteRetorno.getRut())){
                pacienteRetorno.setFichaPaciente(fichaMedica);
                pacienteRetorno.showUserData();
                return pacienteRetorno;
            }
        }
        return pacienteRetorno;
    }

    @Override
    public List<Paciente> getPacientes() {
        List<Paciente> list = new ArrayList<>();
        query = "SELECT * FROM paciente inner join usuario where paciente.idUsuario=usuario.id";
        Paciente pacienteRetorno=null;
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaCreacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Paciente dato = new Paciente(
                        resultado.getInt("idUsuario"),
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
            if(fichaMedicaDAO.getFichaPaciente(paciente.getRut()) != null){
                out.print("tiene ficha");
                FichaMedica fichaMedica= fichaMedicaDAO.getFichaPaciente(paciente.getRut());
                paciente.setFichaPaciente(fichaMedica);
                paciente.showUserData();
                out.print("\n");
            }
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
                query = "INSERT INTO paciente (idUsuario,fechaNacimiento,emailAlternativo,telefonoAlternativo,prevision,nacionalidad) VALUES (?,?,?,?,?,?)";
                conexion = DataBase.conectar();
                sentencia = conexion.prepareStatement(query);
                sentencia.setInt(1,resultadoParaEnteros);
                sentencia.setDate(2, (java.sql.Date) paciente.getFechaNacimiento());
                sentencia.setString(3,paciente.getEmailAlternativo());
                sentencia.setString(4,paciente.getTelefonoAlternativo());
                sentencia.setInt(5,paciente.getPrevision().getValor());
                sentencia.setString(6,paciente.getNacionalidad());

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
                        resultado.getInt("idUsuario"),
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
                        resultado.getInt("idUsuario"),
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
    public boolean deletePaciente(int id) { return usuarioDAO.deleteUsuario(id);}

    @Override
    public boolean deletePaciente(Paciente paciente) { return usuarioDAO.deleteUsuario(paciente.getId());}

    @Override
    public boolean deletePaciente(String rut) { return usuarioDAO.deleteUsuario(rut);}


}
