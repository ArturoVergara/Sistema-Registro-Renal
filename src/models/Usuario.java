package models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Usuario
{
    private final int id;
    private final String rut;
    private final String contrasena;
    private final String nombre;
    private final String direccion;
    private final String email;
    private final String telefono;
    private LocalDateTime fechaCreacion;

    //Constructor para la aplicación
    public Usuario(String rut, String contrasena, String nombre, String direccion, String email, String telefono)
    {
        this.id = 0;
        this.rut = rut;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.fechaCreacion = LocalDateTime.now();
    }

    //Constructor para MySQL (Base de Datos)
    public Usuario(int id, String rut, String contrasena, String nombre, String direccion, String email, String telefono, LocalDateTime fechaCreacion)
    {
        this.id = id;
        this.rut = rut;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public String getRut() {
        return rut;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getRut(), usuario.getRut()) &&
                Objects.equals(getNombre(), usuario.getNombre()) &&
                Objects.equals(getDireccion(), usuario.getDireccion()) &&
                Objects.equals(getEmail(), usuario.getEmail()) &&
                Objects.equals(getTelefono(), usuario.getTelefono());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRut(), getNombre(), getDireccion(), getEmail(), getTelefono());
    }
}
