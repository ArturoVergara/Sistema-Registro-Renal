package DAO;

import models.Diagnostico;
import models.Examen;

import java.util.List;

public interface ExamenDAO {

    Diagnostico getExamenPaciente(String rut);

    Diagnostico getExamenPaciente(int id);

    List<Diagnostico> getExamenes();

    Diagnostico createExamenPaciente(Examen examen);

    Diagnostico updateExamenPaciente(Examen examen);

    void deleteExamenPaciente(int id);
}

