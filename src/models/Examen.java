package models;

import models.enums.ExamenEnum;
import models.enums.PersonalEnum;

import java.time.LocalDateTime;

public class Examen{

    private final int id;
    private final LocalDateTime fechaEmision;
    private ExamenEnum tipoExamen;
    private float resultadoExamen;

    public Examen(int id, LocalDateTime fechaEmision, int tipoExamen, float resultadoExamen){
        this.id=id;
        this.fechaEmision=fechaEmision;
        this.tipoExamen= this.getTipoExamenExamen(tipoExamen);
        this.resultadoExamen=resultadoExamen;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public ExamenEnum getTipoExamen() {
        return tipoExamen;
    }

    public void setTipoExamen(ExamenEnum tipoExamen) {
        this.tipoExamen = tipoExamen;
    }

    public float getResultadoExamen() {
        return resultadoExamen;
    }

    public void setResultadoExamen(float resultadoExamen) {
        this.resultadoExamen = resultadoExamen;
    }

    public int getTipoExamenInt(){
        if(this.tipoExamen == ExamenEnum.CREATININA){
            return 1;
        }
        if(this.tipoExamen == ExamenEnum.ALBUMINA){
            return 2;
        }
        if(this.tipoExamen == ExamenEnum.UREA){
            return 3;
        }
        return 0;
    }

    public ExamenEnum getTipoExamenExamen(int i){
        if(i == 1){
            return ExamenEnum.CREATININA;
        }
        if(i == 2){
            return ExamenEnum.ALBUMINA;
        }
        if(i == 3){
           return ExamenEnum.UREA;
        }
        return null;
    }
}
