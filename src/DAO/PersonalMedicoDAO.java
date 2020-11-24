package DAO;

import models.PersonalMedico;

import java.util.List;

public interface PersonalMedicoDAO {

    PersonalMedico getPersonalMedico(String rut);

    PersonalMedico getPersonalMedico(PersonalMedico personalMedico);

    PersonalMedico getPersonalMedico(int id);

    List<PersonalMedico> getAllPersonalMedico();

    PersonalMedico createPersonalMedico(PersonalMedico personalMedico);

    PersonalMedico updatePersonalMedico(PersonalMedico personalMedico);

    boolean deletePersonalMedico(String rut);

    boolean deletePersonalMedico(PersonalMedico personalMedico);

    boolean deletePersonalMedico(int id);
}
