package DAO;

import models.Paciente;
import models.PersonalMedico;
import models.Usuario;
import java.util.List;

public interface UsuarioDAO {
    Usuario getUsuario(String rut);
    Usuario getUsuario(int id);
    List<Usuario> getUsuarios();
    Usuario createUsuario(Usuario usuario);
    void deleteUsuario(String rut);
    boolean testCredentialsPersonal(String rut, String contrasena);
    //boolean testCredentialsPaciente(Paciente paciente, String rut, String contrasena);


}
