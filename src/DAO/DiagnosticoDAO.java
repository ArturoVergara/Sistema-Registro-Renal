package DAO;

import models.Diagnostico;
import models.Usuario;

import java.util.List;

public interface DiagnosticoDAO {

    Diagnostico getDiagnosticoPaciente(String rut);

    Diagnostico getDiagnosticoPaciente(int id);

    List<Diagnostico> getDiagnosticosPaciente();

    Diagnostico createDiagnosticoPaciente(Diagnostico diagnostico);

    Diagnostico updateDiagnostico(Diagnostico diagnostico);

    List<Diagnostico> agregarDiagnosticoPaciente(Diagnostico diagnostico);

    void deleteDiagnosticoPaciente(int id);
}
