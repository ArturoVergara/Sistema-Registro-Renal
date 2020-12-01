package models;

import lombok.Getter;
import lombok.Setter;
import models.enums.CategoriaDanioEnum;

import java.time.LocalDateTime;

@Setter
@Getter
public class Diagnostico{

    private final int id;
    private LocalDateTime fechaActualizacion;
    private float resultadoFiltradoGlomerular;
    private String descripcionDiagnostico;
    private CategoriaDanioEnum categoriaDanioPaciente;

    public Diagnostico(CategoriaDanioEnum categoriaDanioEnum, float resultadoFiltradoGlomerular, String descripcionDiagnostico)
    {
        this.id = 0;
        this.fechaActualizacion = LocalDateTime.now();
        this.categoriaDanioPaciente = categoriaDanioEnum;
        this.resultadoFiltradoGlomerular = resultadoFiltradoGlomerular;
        this.descripcionDiagnostico = descripcionDiagnostico;
    }

    public Diagnostico(int id, LocalDateTime fechaActualizacion, float resultadoExamen, String desc, int categoriaDanio){
        this.id=id;
        this.fechaActualizacion=fechaActualizacion;
        this.resultadoFiltradoGlomerular=resultadoExamen;
        this.descripcionDiagnostico=desc;
        this.categoriaDanioPaciente=this.getCategoriaDanio(categoriaDanio);
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
