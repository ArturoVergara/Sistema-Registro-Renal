package models;

import java.time.LocalDateTime;
import java.util.Date;

public class Paciente extends Usuario{

    private Date fechaNacimiento;
    private String nacionalidad;
    private String prevision;
    private String telefonoAlternativo;
    private String emailAlternativo;
    private FichaMedica fichaPaciente;

    public Paciente(String rut, String contrasena, String nombre, String direccion, String email, String telefono) {
        super(rut, contrasena, nombre, direccion, email, telefono);
    }

    public Paciente(int id, String rut, String contrasena, String nombre, String direccion, String email, String telefono, LocalDateTime fechaCreacion) {
        super(id, rut, contrasena, nombre, direccion, email, telefono, fechaCreacion);
    }

    public FichaMedica getFichaPaciente(){
        return fichaPaciente;
    }

    public void setFichaPaciente(FichaMedica fichaPaciente1){
        this.fichaPaciente = fichaPaciente1;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getPrevision() {
        return prevision;
    }

    public void setPrevision(String prevision) {
        this.prevision = prevision;
    }

    public String getTelefonoAlternativo() {
        return telefonoAlternativo;
    }

    public void setTelefonoAlternativo(String telefonoAlternativo) {
        this.telefonoAlternativo = telefonoAlternativo;
    }

    public String getEmailAlternativo() {
        return emailAlternativo;
    }

    public void setEmailAlternativo(String emailAlternativo) {
        this.emailAlternativo = emailAlternativo;
    }
}
