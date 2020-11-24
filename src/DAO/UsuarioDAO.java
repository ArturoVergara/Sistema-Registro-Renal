package DAO;

import models.Usuario;
import java.util.List;

public interface UsuarioDAO {

    Usuario getUsuario(String rut);

    Usuario getUsuario(int id);

    List<Usuario> getUsuarios();

    Usuario createUsuario(Usuario usuario);

    boolean deleteUsuario(String rut);

    boolean deleteUsuario(int id);

    boolean testCredentialsPersonal(String rut, String contrasena);

    void cambiarContrasena(Usuario usuario);

    void crearContrasena(Usuario usuario);
}
