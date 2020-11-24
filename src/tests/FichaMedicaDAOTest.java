package tests;

import core.DataBase;
import models.Diagnostico;
import models.Examen;
import models.FichaMedica;
import models.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

public class FichaMedicaDAOTest {

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultado2;
    public String query;

    @org.junit.jupiter.api.Test
    void getFichaPaciente2() {
        String rut= "14.501.741-6";
        query = "SELECT fm.* from fichamedica AS fm inner join paciente as p on fm.idPaciente=p.id inner join usuario as u on p.idUsuario=u.id where u.rut =?";
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
                        resultado.getInt("etnia"),
                        timestamp.toLocalDateTime()
                );
                fichaMedicaRetorno=dato;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(fichaMedicaRetorno!=null){
            fichaMedicaRetorno.showFichaData();
        }
        //return fichaMedicaRetorno;
    }

    @org.junit.jupiter.api.Test
    void getFichaPaciente() {
        int id=1;
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
                        resultado.getInt("etnia"),
                        timestamp.toLocalDateTime()
                );
                fichaMedicaRetorno=dato;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assert fichaMedicaRetorno != null;
        fichaMedicaRetorno.showFichaData();
        //return fichaMedicaRetorno;
    }

    @org.junit.jupiter.api.Test
    void getExamenesPaciente() {
        query = "SELECT * FROM EXAMEN as E JOIN fichamedica as FM ON E.idFicha=1 WHERE E.idFicha = FM.id";
        List<Examen> lista = new ArrayList<>();
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            //sentencia.setInt(1,fichaMedica.getId());
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaEmision");
                Timestamp timestamp = new Timestamp(date.getTime());

                Examen dato = new Examen(
                        resultado.getInt("id"),
                        timestamp.toLocalDateTime(),
                        resultado.getInt("tipo"),
                        resultado.getFloat("valor")
                );
                lista.add(dato);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (Examen examen : lista) {
            System.out.println(examen.getTipoExamenInt() + " " + examen.getFechaEmision());
        }
        //return lista;
    }

    @org.junit.jupiter.api.Test
    void getDiagnosticosPaciente() {
        query = "SELECT * FROM diagnostico as D JOIN fichamedica as FM ON D.idFicha=1 WHERE D.idFicha = FM.id";
        List<Diagnostico> lista = new ArrayList<>();
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            //sentencia.setInt(1,fichaMedica.getId());
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                Date date = resultado.getDate("fechaActualizacion");
                Timestamp timestamp = new Timestamp(date.getTime());


                Diagnostico dato = new Diagnostico(
                        resultado.getInt("id"),
                        timestamp.toLocalDateTime(),
                        resultado.getFloat("resultado"),
                        resultado.getString("descripcion"),
                        resultado.getInt("categoriaDanio")
                );
                lista.add(dato);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (Diagnostico diagnostico : lista) {
            System.out.println(diagnostico.getFechaActualizacion()+ " " + diagnostico.getCategoriaDanioPaciente());
        }
    }

    @org.junit.jupiter.api.Test
    void getUltimoDiagnostico() {
        query = "SELECT * FROM diagnostico AS D INNER JOIN fichamedica AS FM ON D.idFicha=1 ORDER BY fechaActualizacion DESC LIMIT 1;";
        Diagnostico diagnostico = null;
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            //sentencia.setInt(1,fichaMedica.getId());
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Date date = resultado.getDate("fechaActualizacion");
                Timestamp timestamp = new Timestamp(date.getTime());
                Diagnostico dato = new Diagnostico(
                        resultado.getInt("id"),
                        timestamp.toLocalDateTime(),
                        resultado.getFloat("resultado"),
                        resultado.getString("descripcion"),
                        resultado.getInt("categoriaDanio")
                );
                diagnostico= dato;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (diagnostico!=null){
            diagnostico.showDiagnosticoData();
            //return diagnostico;
        }
    }

    @org.junit.jupiter.api.Test
    void createFichaPaciente() {
        /**
         * Se crea usuario y se guarda en la db
         * Se retorna el objeto usuario si se pudo guardar satisfactoriamente
         * Se retorna null si hubo un error al guardar el usuario
         */
        FichaMedica fichaMedica = new FichaMedica(true,10,169,1);
        /*
         *lo primero es obtener la id del paciente en la db
         */
        Paciente paciente =null;
        query = "SELECT * FROM paciente AS P INNER JOIN usuario as U WHERE P.idUsuario=1";
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
                paciente=dato;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        query = "INSERT INTO fichamedica (idPaciente,sexo,peso,altura,etnia,fechaCreacion) VALUES (?,?,?,?,?,now())";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,paciente.getId());
            sentencia.setInt(2,fichaMedica.getSexo());
            sentencia.setFloat(3,fichaMedica.getPesoPaciente());
            sentencia.setFloat(4,fichaMedica.getAlturaPaciente());
            sentencia.setInt(5,fichaMedica.getEtniaPaciente());
            resultado2 = sentencia.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultado2>0) {
            out.println("Ficha medica creada satisfactoriamente!");
            fichaMedica.showFichaData();
            //return fichaMedica;
        }else{
            out.println("Error al crear la Ficha medica!");
            //return null;
        }
    }
}
