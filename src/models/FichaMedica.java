package models;


import java.time.LocalDateTime;
import java.util.List;

public class FichaMedica {

    private final int id;
    private boolean sexoPaciente;
    private float pesoPaciente;
    private float alturaPaciente;
    private String etniaPaciente;
    List<Diagnostico> diagnosticosPaciente;
    List<Examen> examenesPaciente;
    private final LocalDateTime fechaCreacion;


    public FichaMedica(int id, boolean sexoPaciente, float pesoPaciente, float alturaPaciente, String etniaPaciente){
        this.id = id;
        this.sexoPaciente = sexoPaciente;
        this.pesoPaciente= pesoPaciente;
        this.alturaPaciente=alturaPaciente;
        this.etniaPaciente=etniaPaciente;
        this.fechaCreacion=LocalDateTime.now();
        this.diagnosticosPaciente = null;
        this.examenesPaciente=null;
    }

    public FichaMedica(int id, boolean sexoPaciente, float pesoPaciente, float alturaPaciente, String etniaPaciente, LocalDateTime fechaCreacion){
        this.id = id;
        this.fechaCreacion=fechaCreacion;
        this.sexoPaciente = sexoPaciente;
        this.pesoPaciente= pesoPaciente;
        this.alturaPaciente=alturaPaciente;
        this.etniaPaciente=etniaPaciente;
        this.diagnosticosPaciente = null;
        this.examenesPaciente=null;
    }

    public int getId() {
        return id;
    }

    public boolean isSexoPaciente() {
        return sexoPaciente;
    }

    public void setSexoPaciente(boolean sexoPaciente) {
        this.sexoPaciente = sexoPaciente;
    }

    public float getPesoPaciente() {
        return pesoPaciente;
    }

    public void setPesoPaciente(float pesoPaciente) {
        this.pesoPaciente = pesoPaciente;
    }

    public float getAlturaPaciente() {
        return alturaPaciente;
    }

    public void setAlturaPaciente(float alturaPaciente) {
        this.alturaPaciente = alturaPaciente;
    }

    public String getEtniaPaciente() {
        return etniaPaciente;
    }

    public void setEtniaPaciente(String etniaPaciente) {
        this.etniaPaciente = etniaPaciente;
    }

    public List<Diagnostico> getDiagnosticosPaciente() {
        return diagnosticosPaciente;
    }

    public void setDiagnosticosPaciente(List<Diagnostico> diagnosticosPaciente) {
        this.diagnosticosPaciente = diagnosticosPaciente;
    }

    public List<Examen> getExamenesPaciente() {
        return examenesPaciente;
    }

    public void setExamenesPaciente(List<Examen> examenesPaciente) {
        this.examenesPaciente = examenesPaciente;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
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
