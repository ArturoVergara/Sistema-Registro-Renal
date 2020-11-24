package DAO;

import models.Diagnostico;
import models.Examen;
import models.FichaMedica;
import models.Paciente;

import java.util.List;

public interface FichaMedicaDAO {

    FichaMedica getFichaPaciente(String rut);

    FichaMedica getFichaPaciente(int id);

    List<Examen> getExamenesPaciente(FichaMedica fichaMedica);

    List<Diagnostico> getDiagnosticosPaciente(FichaMedica fichaMedica);

    List<Diagnostico> getUltimoDiagnostico(FichaMedica fichaMedica);

    FichaMedica createFichaPaciente(FichaMedica fichaMedica);

    FichaMedica updateFichaPaciente(FichaMedica fichaMedica);

    Diagnostico agregarDiagnosticoAFicha(Diagnostico diagnostico);

    Examen agregarExamenAFicha(Examen examen);

    FichaMedica deleteFichaPaciente(int idFicha);

    FichaMedica deleteFichaPaciente(Paciente paciente);

    FichaMedica deleteFichaPaciente(String rutPaciente);
}
