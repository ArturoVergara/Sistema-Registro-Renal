package models;

import models.enums.PersonalEnum;

import java.time.LocalDateTime;

public class PersonalMedico extends Usuario{

    private PersonalEnum tipoPersonal;

    public PersonalMedico(String rut, String contrasena, String nombre, String direccion, String email, String telefono) {
        super(rut, contrasena, nombre, direccion, email, telefono);
    }

    public PersonalMedico(int id, String rut, String contrasena, String nombre, String direccion, String email, String telefono, LocalDateTime fechaCreacion) {
        super(id, rut, contrasena, nombre, direccion, email, telefono, fechaCreacion);
    }

    public boolean isAdmin(){
        if(this.tipoPersonal == PersonalEnum.ADMIN){
            return true;
        }
        return false;
    }

    public PersonalEnum getTipoPersonal() {
        return tipoPersonal;
    }

    public int getTipoPersonalInt(){
        if(this.tipoPersonal == PersonalEnum.ADMIN){
            return 1;
        }
        if(this.tipoPersonal == PersonalEnum.DOCTOR){
            return 2;
        }
        if(this.tipoPersonal == PersonalEnum.LABORISTA){
            return 3;
        }
        if(this.tipoPersonal == PersonalEnum.ENFERMERO){
            return 4;
        }
        if(this.tipoPersonal == PersonalEnum.GES) {
            return 5;
        }
        return 0;
    }

    public void setTipoPersonal(PersonalEnum tipoPersonal) {
        this.tipoPersonal = tipoPersonal;
    }
}
