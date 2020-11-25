package DAO;

import models.Diagnostico;
import models.Examen;
import models.Paciente;

import java.util.List;

public interface ExamenDAO {

    Examen getExamenPaciente(String rut);

    Examen getExamenPaciente(int id);

    List<Diagnostico> getExamenes();

    Examen createExamenPaciente(Paciente paciente, Examen examen);

    Examen updateExamenPaciente(Examen examen);

    boolean deleteExamenPaciente(int idExamen);
}

