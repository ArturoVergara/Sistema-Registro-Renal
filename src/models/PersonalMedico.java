package models;

import lombok.Getter;
import lombok.Setter;
import models.enums.PersonalEnum;

import java.time.LocalDateTime;

@Setter
@Getter
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
        return this.tipoPersonal == PersonalEnum.ADMIN;
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
