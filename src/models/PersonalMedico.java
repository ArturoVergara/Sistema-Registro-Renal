package models;

import models.enums.PersonalEnum;

import java.time.LocalDateTime;

public class PersonalMedico extends Usuario{

    private PersonalEnum tipoPersonal;

    public PersonalMedico(String rut, String nombre, String direccion, String email, String telefono) {
        super(rut, nombre, null, direccion, email, telefono);
    }

    public PersonalMedico(int id, String rut, String nombre, String direccion, String email, String telefono, LocalDateTime fechaCreacion, int tipoPersonal) {
        super(id, rut, nombre, direccion, email, telefono, fechaCreacion);
        this.tipoPersonal= this.getTipoPersonalPersonal(tipoPersonal);
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

    public PersonalEnum getTipoPersonalPersonal(int i){
        if(i == 1){
            return PersonalEnum.ADMIN;
        }
        if(i == 2){
            return PersonalEnum.DOCTOR;
        }
        if(i == 3){
            return PersonalEnum.LABORISTA;
        }
        if(i == 4){
            return PersonalEnum.ENFERMERO;
        }
        if(i == 5) {
            return PersonalEnum.GES;
        }
        return null;
    }

    public void setTipoPersonal(PersonalEnum tipoPersonal) {
        this.tipoPersonal = tipoPersonal;
    }

    public void showUserData(){
        System.out.print(
                "Id: " + this.getId() + "\n" +
                        "Nombre: " + this.getNombre() + "\n" +
                        "Rut: " +     this.getRut() + "\n" +
                        "Telefono: " + this.getTelefono()+ "\n" +
                        "Direccion: " + this.getDireccion() + "\n" +
                        "Email: " + this.getEmail() + "\n"
        );
    }
}
