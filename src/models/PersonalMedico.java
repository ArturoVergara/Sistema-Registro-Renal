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

    public PersonalEnum getTipoPersonal() {
        return tipoPersonal;
    }

    public void setTipoPersonal(PersonalEnum tipoPersonal) {
        this.tipoPersonal = tipoPersonal;
    }
}
