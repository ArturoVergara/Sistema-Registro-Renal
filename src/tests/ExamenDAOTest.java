package tests;

import core.DataBase;
import models.Examen;
import models.FichaMedica;
import models.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static java.lang.System.out;

public class ExamenDAOTest {

    private static Connection conexion;
    private static PreparedStatement sentencia;
    private static ResultSet resultado;
    private static int resultado2;
    public String query;

    @org.junit.jupiter.api.Test
    void deleteExamenPaciente() {
        int idExamen=2;
        /*query = "SELECT fm.* from fichamedica as fm join examen as e on e.idFicha=fm.id where e.id=?";
        FichaMedica fichaMedicaRetorno = null;
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,idExamen);
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
            List<Examen> examenList = fichaMedicaRetorno.getExamenesPaciente();
            examenList.removeIf(examen -> examen.getId() == idExamen);
        }else{
            System.out.print("asdaasd error");
        }*/

        query = "DELETE FROM examen where examen.id=?";
        try {
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setInt(1,idExamen);
            resultado2 = sentencia.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(resultado2>0){
            System.out.print("went good");

        }else {
            System.out.print("asdaasd error");
        }
        //return resultado2 > 0;
    }

    @org.junit.jupiter.api.Test
    void createExamenPaciente() {
        Examen examen = new Examen(LocalDateTime.now(),1,12);
        Paciente paciente = new Paciente(2,"19940860-7","FELIPE GUAJARDO NUNEZ","BRASIL 58","AGN@MAIL.CL","82017717",
                LocalDateTime.now(), Date.from(Instant.now()),"Chileno",1,"telefono","mailalternativotest");
        /*query = "SELECT fm.id from fichamedica as fm inner join paciente as p on fm.idPaciente=p.id inner join usuario as u on p.idUsuario=u.id where u.rut=?";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1,rut);

            resultado = sentencia.executeQuery();
            resultado.next();
            resultado2 = resultado.getInt("id");

        }catch (Exception e){
            e.printStackTrace();
        }

        out.print(resultado2);
*/
        query = "INSERT INTO examen (idFicha,fechaEmision,tipo,valor) VALUES (?,now(),?,?)";
        try{
            conexion = DataBase.conectar();
            sentencia = conexion.prepareStatement(query);

            sentencia.setInt(1,paciente.getFichaPaciente().getId());
            sentencia.setInt(2,examen.getTipoExamen().getValor());
            sentencia.setFloat(3,examen.getResultadoExamen());
            resultado2 = sentencia.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        if(resultado2>0){
            out.println("Examen: ");
            examen.showExamenData();
            out.println("\ncreado satisfactoriamente!");
            //return examen;
        } else {
            //return null;
            out.println("asdassd: ");
        }
    }
}
