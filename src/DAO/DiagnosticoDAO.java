package DAO;

import models.Diagnostico;
import models.Paciente;

import java.util.List;

public interface DiagnosticoDAO {

    Diagnostico getDiagnosticoPaciente(String rut);

    Diagnostico getDiagnosticoPaciente(int id);

    List<Diagnostico> getDiagnosticosPaciente();

    Diagnostico createDiagnosticoPaciente(Diagnostico diagnostico);

    Diagnostico updateDiagnostico(Diagnostico diagnostico);

    Diagnostico agregarDiagnosticoPaciente(Paciente paciente, Diagnostico diagnostico);

    boolean deleteDiagnosticoPaciente(int idDiagnostico);
}
