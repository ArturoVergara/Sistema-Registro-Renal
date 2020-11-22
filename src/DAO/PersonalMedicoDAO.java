package DAO;

import models.PersonalMedico;
import models.Usuario;

import java.util.List;

public interface PersonalMedicoDAO {
    PersonalMedico getPersonalMedico(String rut);
    List<PersonalMedico> getPersonalMedico();
    PersonalMedico createPersonalMedico(PersonalMedico personalMedico);
    void deletePersonalMedico(String rut);
}
