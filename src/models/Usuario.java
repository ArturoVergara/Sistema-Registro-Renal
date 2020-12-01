package models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
public class Usuario
{
    private  int id;
    private String rut;
    private String contrasena;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private final LocalDateTime fechaCreacion;

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

    public Usuario(int id, String rut, String nombre , String contrasena, String direccion, String email, String telefono, LocalDateTime fechaCreacion)
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

    //Constructor para MySQL (Base de Datos)
    public Usuario(int id, String rut, String nombre, String direccion, String email, String telefono, LocalDateTime fechaCreacion)
    {
        this.id = id;
        this.rut = rut;
        this.contrasena = null;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario(int id, String rut, String nombre, String direccion, String email, String telefono)
    {
        this.id = id;
        this.rut = rut;
        this.contrasena = this.getContrasena();
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.fechaCreacion= this.getFechaCreacion();
    }



    public void showUserData(Usuario usuario){
        System.out.println("Datos del usuario: ");
        System.out.println(
                        "Nombre: " + usuario.getNombre() + " " +
                        "Rut: " +     usuario.getRut() + " " +
                        "Telefono: " + usuario.getTelefono()+ " " +
                        "Direccion: " + usuario.getDireccion() +".\n"
        );
    }


    public boolean testCredentials(String rut, String contrasena){
        return (this.rut.equals(rut) && this.contrasena.equals(contrasena));
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
