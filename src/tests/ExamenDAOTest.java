package tests;

import core.DataBase;
import models.Examen;
import models.FichaMedica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
}
