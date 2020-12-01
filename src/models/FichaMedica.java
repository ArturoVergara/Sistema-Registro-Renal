package models;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
public class FichaMedica {

    private final int id;
    private boolean sexoPaciente; //TRUE -> HOMBRE; FALSE->MUJER
    private boolean etniaPaciente; // TRUE-> BLANCO; FALSE->NEGRO
    private float pesoPaciente;
    private float alturaPaciente;
    List<Diagnostico> diagnosticosPaciente;
    List<Examen> examenesPaciente;
    private final LocalDateTime fechaCreacion;


    public FichaMedica(boolean sexoPaciente, float pesoPaciente, float alturaPaciente, int etniaPaciente){
        this.id = 0;
        this.sexoPaciente = sexoPaciente;
        this.pesoPaciente= pesoPaciente;
        this.alturaPaciente=alturaPaciente;
        this.setEtniaPaciente(etniaPaciente);
        this.fechaCreacion=LocalDateTime.now();
        this.diagnosticosPaciente = null;
        this.examenesPaciente=null;
    }

    public FichaMedica(int id, boolean sexoPaciente, float pesoPaciente, float alturaPaciente, int etniaPaciente, LocalDateTime fechaCreacion){
        this.id = id;
        this.fechaCreacion=fechaCreacion;
        this.sexoPaciente = sexoPaciente;
        this.pesoPaciente= pesoPaciente;
        this.alturaPaciente=alturaPaciente;
        this.setEtniaPaciente(etniaPaciente);
        this.diagnosticosPaciente = null;
        this.examenesPaciente=null;
    }

    public int getSexo(){
        if(isSexoPaciente()){
            return 1;
        }
        else{
            return 0;
        }
    }

    public int getEtniaPaciente() {
        if(this.etniaPaciente){
            return 1;
        }
        return 0;
    }

    public void setEtniaPaciente(int i) {
        this.etniaPaciente= i == 1;
    }

    public void showFichaData(){
        System.out.print(
                        "Id: " + this.getId() + "\n" +
                        "Fecha de creacion de la ficha medica: " + this.getFechaCreacion() + "\n" +
                        "Examenes del paciente: " +     this.getExamenesPaciente() + "\n" +
                        "Diagnosticos del paciente: " + this.getDiagnosticosPaciente()+ "\n" +
                        "Etnia del paciente: " + this.getEtniaPaciente() + "\n" +
                        "Altura en Metros del paciente: " + this.getAlturaPaciente() + "\n" +
                        "Peso en KG del paciente: " + this.getPesoPaciente() + "\n"
        );
    }
}
