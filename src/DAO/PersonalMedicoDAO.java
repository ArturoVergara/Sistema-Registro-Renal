package DAO;

import models.Paciente;
import models.PersonalMedico;
import models.Usuario;

import java.util.List;

public interface PersonalMedicoDAO {
    PersonalMedico getPersonalMedico(String rut);
    PersonalMedico getPersonalMedico(PersonalMedico personalMedico);
    PersonalMedico getPersonalMedico(int id);

    List<PersonalMedico> getAllPersonalMedico();
    PersonalMedico createPersonalMedico(PersonalMedico personalMedico);
    PersonalMedico updatePersonalMedico(PersonalMedico personalMedico);

    void deletePersonalMedico(String rut);

    void deletePersonalMedico(PersonalMedico personalMedico);

    void deletePersonalMedico(int id);
}
