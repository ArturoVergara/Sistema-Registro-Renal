package controllers;

import DAO.PacienteDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Paciente;
import models.PersonalMedico;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;

public class PerfilPacienteController implements Initializable
{
    @FXML
    private BorderPane parentContainer;
    @FXML
    private Label nombreUsuario;
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
        nombreUsuario.setText(usuario.getNombre());

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

    @FXML
    private void eliminarFichaMedica(ActionEvent evento)
    {
        if (alertaEliminar())
        {
            //PacienteDAOImpl pacienteDAO = new PacienteDAOImpl();

            //pacienteDAO.deletePaciente(dato.getId());
            //tabla.getItems().remove(indice);
            alertaInfo();
        }
    }

    @FXML
    private void cargarVistaModificarFichaMedica(ActionEvent evento)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/FormularioModificarFichaMedica.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de FormularioAgregarFichaMedica
            FormularioModificarFichaMedicaController controlador = (FormularioModificarFichaMedicaController) loader.getController();
            controlador.inicializar(usuario, paciente);

            Stage ventana = (Stage) parentContainer.getScene().getWindow();
            ventana.setScene(escena);
            ventana.show();
        }
        catch (IOException | IllegalStateException excepcion)
        {
            alertaExcepcion(excepcion);
        }
    }

    //Muestra un cuadro de dialogo, donde pide confirmación para eliminar el paciente, retornando un booleano
    private boolean alertaEliminar()
    {
        Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);
        String contenido = "Paciente: "+paciente.getNombre()+"\nRut: "+paciente.getRut();

        ventana.setTitle("Confirmar Eliminación de Ficha Médica");
        ventana.setHeaderText(contenido);
        ventana.initStyle(StageStyle.UTILITY);
        ventana.setContentText("¿Realmente desea eliminar la ficha médica de este paciente?");


        Optional<ButtonType> opcion=ventana.showAndWait();

        if (opcion.get() == ButtonType.OK)
            return true;

        return false;
    }

    private void alertaInfo()
    {
        Alert ventana=new Alert(Alert.AlertType.INFORMATION);
        ventana.setTitle("¡Éxito al eliminar!");
        ventana.setHeaderText("Se ha eliminado la ficha médica del paciente satisfactioramente.");
        ventana.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        ventana.showAndWait();
    }

    //Muestra una alerta con toda la información detallada de la excepción
    private void alertaExcepcion(Exception excepcion)
    {
        Alert alerta = new Alert(Alert.AlertType.ERROR);

        alerta.setTitle("Alerta Excepción");
        alerta.setHeaderText(excepcion.getMessage());
        alerta.setContentText(excepcion.toString());

        //Se imprime el stacktrace de la excepcion en un cajón expandible de texto
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        excepcion.printStackTrace(pw);
        TextArea texto = new TextArea(sw.toString());
        texto.setEditable(false);
        texto.setWrapText(true);
        texto.setMaxWidth(Double.MAX_VALUE);
        texto.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(texto, Priority.ALWAYS);
        GridPane.setHgrow(texto, Priority.ALWAYS);
        GridPane contenido = new GridPane();
        contenido.setMaxWidth(Double.MAX_VALUE);
        contenido.add(new Label("El Stacktrace de la excepción fue:"),0,0);
        contenido.add(texto,0, 1);

        //Se ajusta el texto en la alerta y se muestra por pantalla
        alerta.getDialogPane().setExpandableContent(contenido);
        alerta.showAndWait();
    }
}
