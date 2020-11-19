package DAO;

import models.Usuario;
import java.util.List;

public interface UsuarioDAO {
    Usuario getUsuario(String rut);
    Usuario getUsuario(int id);
    List<Usuario> getUsuarios();
    Usuario createUsuario(Usuario usuario);
    void deleteUsuario(String rut);
}
