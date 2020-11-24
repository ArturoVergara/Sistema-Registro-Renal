package models;

import models.enums.CategoriaDanioEnum;

import java.time.LocalDateTime;

public class Diagnostico{

    private final int id;
    private final LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private float resultadoFiltradoGlomerular;
    private String descripcionDiagnostico;
    private CategoriaDanioEnum categoriaDanioPaciente;

    public Diagnostico(int id,LocalDateTime fechaCreacion, float resultadoExamen, String desc, CategoriaDanioEnum categoriaDanio){
        this.id=id;
        this.fechaCreacion=fechaCreacion;
        this.resultadoFiltradoGlomerular=resultadoExamen;
        this.descripcionDiagnostico=desc;
        this.categoriaDanioPaciente=categoriaDanio;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
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
}
