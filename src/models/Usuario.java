package models;

import java.time.LocalDateTime;

public class Usuario
{
    private int id;
    private String rut;
    private String contrasena;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
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
    public Usuario(int id, String rut, String contrasena, String nombre, String direccion, String email, String telefono, String fechaCreacion)
    {
        this.id = id;
        this.rut = rut;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;

        //Convertir la fecha de MySQL a LocalDateTime de Java
        //this.fechaCreacion = LocalDateTime.now();
    }

    public String getNombre()
    {
        return this.nombre;
    }

    public String getRut()
    {
        return this.rut;
    }
}
