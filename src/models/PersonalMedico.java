package models;

import models.enums.PersonalEnum;

import java.time.LocalDateTime;

public class PersonalMedico extends Usuario{

    private PersonalEnum tipoPersonal;

    public PersonalMedico(String rut, String nombre, String contrasena, String direccion, String email, String telefono, PersonalEnum tipoPersonal) {
        super(rut, contrasena, nombre, direccion, email, telefono);
        this.tipoPersonal = tipoPersonal;
    }

    public PersonalMedico(int id, String rut, String nombre, String direccion, String email, String telefono, int tipoPersonal) {
        super(id,rut,nombre, direccion, email, telefono);
        this.tipoPersonal = PersonalEnum.fromInteger(tipoPersonal);
    }

    public PersonalMedico(int id, String rut, String nombre, String direccion, String email, String telefono, LocalDateTime fechaCreacion, int tipoPersonal) {
        super(id, rut, nombre, direccion, email, telefono, fechaCreacion);
        this.tipoPersonal= PersonalEnum.fromInteger(tipoPersonal);
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
        if(this.tipoPersonal == PersonalEnum.LABORATORISTA){
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
