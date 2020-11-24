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

    Diagnostico agregarDiagnosticoAFicha(FichaMedica fichaMedica, Diagnostico diagnostico);

    Examen agregarExamenAFicha(FichaMedica fichaMedica, Examen examen);

    boolean deleteFichaPaciente(int idFicha);

    boolean deleteFichaPaciente(FichaMedica fichaMedica);

    boolean deleteFichaPaciente(Paciente paciente);
}
