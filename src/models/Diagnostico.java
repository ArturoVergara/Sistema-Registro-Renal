package models;

import models.enums.CategoriaDanioEnum;

import java.time.LocalDateTime;

public class Diagnostico{

    private final int id;
    private LocalDateTime fechaActualizacion;
    private float resultadoFiltradoGlomerular;
    private String descripcionDiagnostico;
    private CategoriaDanioEnum categoriaDanioPaciente;

    public Diagnostico(int id, LocalDateTime fechaActualizacion, float resultadoExamen, String desc, int categoriaDanio){
        this.id=id;
        this.fechaActualizacion=fechaActualizacion;
        this.resultadoFiltradoGlomerular=resultadoExamen;
        this.descripcionDiagnostico=desc;
        this.categoriaDanioPaciente=this.getCategoriaDanio(categoriaDanio);
    }

    public int getId() {
        return id;
    }


    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public float getResultadoFiltradoGlomerular() {
        return resultadoFiltradoGlomerular;
    }

    public void setResultadoFiltradoGlomerular(float resultado) {
        this.resultadoFiltradoGlomerular = resultado;
    }

    public String getDescripcionDiagnostico() {
        return descripcionDiagnostico;
    }

    public void setDesc(String desc) {
        this.descripcionDiagnostico = desc;
    }

    public CategoriaDanioEnum getCategoriaDanioPaciente() {
        return categoriaDanioPaciente;
    }

    public void setCategoriaDanioPaciente(CategoriaDanioEnum categoriaDanio) {
        this.categoriaDanioPaciente = categoriaDanio;
    }

    public CategoriaDanioEnum getCategoriaDanio(int i){
        if(i == 1){
            return CategoriaDanioEnum.NORMAL;
        }
        if(i == 2){
            return CategoriaDanioEnum.TEMPRANA;
        }
        if(i == 3){
            return CategoriaDanioEnum.MODERADA;
        }
        if(i == 4){
            return CategoriaDanioEnum.SEVERA;
        }
        if(i == 5){
            return CategoriaDanioEnum.TERMINAL;
        }
        return null;
    }

    public void showDiagnosticoData(){
        System.out.print(
                        "Id: " + this.getId() + "\n" +
                        "Último diagnóstico realizado al paciente: " + this.getFechaActualizacion() + "\n" +
                        "Descripción del diagnóstico: " +     this.getDescripcionDiagnostico() + "\n" +
                        "Categoria de daño del paciente (NORMAL/TEMPRANA/MODERADA/SEVERA/TERMINAL): " + this.getCategoriaDanioPaciente()+ "\n" +
                        "Resultado de formula filtrado glomerular: " + this.getResultadoFiltradoGlomerular() + "\n"

        );
    }
}
