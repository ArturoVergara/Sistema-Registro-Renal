package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import models.Paciente;
import models.PersonalMedico;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class PerfilPacienteController implements Initializable
{
    @FXML
    private BorderPane parenteContainer;
    @FXML
    private Label nombrePaciente;
    @FXML
    private Label rutPaciente;
    @FXML
    private Label fechaNacimientoPaciente;
    @FXML
    private Label direccionPaciente;
    @FXML
    private Label previsionPaciente;
    @FXML
    private Label emailPaciente;
    @FXML
    private Label telefonoPaciente;

    private PersonalMedico usuario;
    private Paciente paciente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void inicializar(PersonalMedico usuario, Paciente paciente)
    {
        String prevision = "";

        this.usuario = usuario;
        this.paciente = paciente;

        this.nombrePaciente.setText(paciente.getNombre());
        this.rutPaciente.setText(paciente.getRut());
        this.fechaNacimientoPaciente.setText(new SimpleDateFormat("dd / MM / yyyy").format(paciente.getFechaNacimiento()));
        this.direccionPaciente.setText(paciente.getDireccion());
        this.emailPaciente.setText(paciente.getEmail());
        this.telefonoPaciente.setText(paciente.getTelefono());

        switch (paciente.getPrevision())
        {
            case FONASA:
                prevision = "Fonasa";
                break;

            case ISAPRE:
                prevision = "Isapre";

            default:
                prevision = "Capredena";
        }

        this.previsionPaciente.setText(prevision);
    }
}
