package models;

import lombok.Getter;
import lombok.Setter;
import models.enums.ExamenEnum;

import java.time.*;

@Setter
@Getter
public class Examen{

    private final int id;
    private final LocalDateTime fechaEmision;
    private ExamenEnum tipoExamen;
    private float resultadoExamen;

    public Examen(ExamenEnum tipoExamen, float resultadoExamen)
    {
        this.id = 0;
        this.fechaEmision = LocalDateTime.now();
        this.tipoExamen = tipoExamen;
        this.resultadoExamen = resultadoExamen;
    }

    public Examen(int id, LocalDateTime fechaEmision, int tipoExamen, float resultadoExamen){
        this.id=id;
        this.fechaEmision=fechaEmision;
        this.tipoExamen= this.getTipoExamenExamen(tipoExamen);
        this.resultadoExamen=resultadoExamen;
    }

    public Examen(LocalDateTime fechaEmision, int tipoExamen, float resultadoExamen){
        this.id=0;
        this.fechaEmision=fechaEmision;
        this.tipoExamen= this.getTipoExamenExamen(tipoExamen);
        this.resultadoExamen=resultadoExamen;
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

    public LocalDate parseFechaNacimiento(LocalDateTime fechaEmision){
        Instant instant = fechaEmision.toInstant(ZoneOffset.ofHours(1));
        ZoneId zoneId = ZoneId.of("America/Montreal");
        ZonedDateTime zdt = ZonedDateTime.ofInstant ( instant , zoneId );
        return zdt.toLocalDate();
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

    public void showExamenData(){
        System.out.print(
                        "Id: " + this.getId() + "\n" +
                        "Tipo de examen (CREATININA/ALBUMINA/UREA): " + this.getTipoExamen() + "\n" +
                        "Resultado del examen: " +     this.getResultadoExamen() + "\n" +
                        "Fecha de emision del examen: " + this.getFechaEmision()+ "\n"
        );
    }
}
