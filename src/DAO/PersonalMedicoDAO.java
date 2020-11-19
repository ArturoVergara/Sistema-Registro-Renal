package DAO;

import models.PersonalMedico;
import models.Usuario;

import java.util.List;

public interface PersonalMedicoDAO {
    PersonalMedico getPersonalMedico(int id);
    List<PersonalMedico> getPersonalMedico();
    PersonalMedico createPersonalMedico(PersonalMedico personalMedico);
    void deletePersonalMedico(int id);
}
