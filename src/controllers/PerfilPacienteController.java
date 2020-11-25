package controllers;

import DAO.FichaMedicaDAOImpl;
import DAO.PacienteDAOImpl;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.layout.VBox;
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
    @FXML
    private VBox fichaMedica;
    @FXML
    private Label sexo;
    @FXML
    private Label estatura;
    @FXML
    private Label peso;
    @FXML
    private Label etnia;
    @FXML
    private JFXButton botonCrear;

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

        //System.out.println(paciente.getFichaPaciente());

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

        if (paciente.getFichaPaciente() != null)
        {
            //Se setean todas las weas
            if (paciente.getFichaPaciente().isSexoPaciente())
                sexo.setText("Masculino");
            else
                sexo.setText("Femenino");

            estatura.setText(String.format("%.2f", paciente.getFichaPaciente().getAlturaPaciente()) + " m");
            peso.setText(String.format("%.2f", paciente.getFichaPaciente().getPesoPaciente()) + " Kg");

            if (paciente.getFichaPaciente().getEtniaPaciente() == 1)
                etnia.setText("Blanca");
            else
                etnia.setText("Negra");
        }
        else
        {
            fichaMedica.setVisible(false);
            botonCrear.setVisible(true);
        }

    }

    @FXML
    private void cerrarSesion()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/Login.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de TablaPacientes
            LoginController controlador = (LoginController) loader.getController();

            Stage ventana = (Stage) parentContainer.getScene().getWindow();
            ventana.setScene(escena);
            ventana.show();
        }
        catch (IOException | IllegalStateException excepcion)
        {
            alertaExcepcion(excepcion);
        }
    }

    @FXML
    private void cargarVistaTablaPacientes()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/TablaPacientes.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de TablaPacientes
            TablaPacientesController controlador = (TablaPacientesController) loader.getController();
            controlador.inicializar(usuario);

            Stage ventana = (Stage) parentContainer.getScene().getWindow();
            ventana.setScene(escena);
            ventana.show();
        }
        catch (IOException | IllegalStateException excepcion)
        {
            alertaExcepcion(excepcion);
        }
    }

    @FXML
    private void cargarVistaAgregarFichaMedica()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/FormularioAgregarFichaMedica.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de FormularioAgregarFichaMedica
            FormularioAgregarFichaMedicaController controlador = (FormularioAgregarFichaMedicaController) loader.getController();
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

    @FXML
    private void cargarVistaTablaExamenes()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/TablaExamenes.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de TablaExamenes
            TablaExamenesController controlador = (TablaExamenesController) loader.getController();
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

    @FXML
    private void cargarVistaTablaDiagnosticos()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/TablaDiagnosticos.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de TablaDiagnosticos
            TablaDiagnosticosController controlador = (TablaDiagnosticosController) loader.getController();
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

    private void alertaError()
    {
        Alert ventana=new Alert(Alert.AlertType.ERROR);
        ventana.setTitle("¡Error al eliminar!");
        ventana.setHeaderText("Error: No se pudo eliminar de la base de datos");
        ventana.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        ventana.showAndWait();
    }


    @FXML
    private void eliminarPaciente(ActionEvent evento)
    {
        if (alertaEliminarPaciente())
        {
            PacienteDAOImpl pacienteDAO = new PacienteDAOImpl();

            if (pacienteDAO.deletePaciente(paciente.getId()))
            {
                alertaInfo();
                cargarVistaTablaPacientes();
            }
            else
                alertaError();
        }
    }

    @FXML
    private void eliminarFichaMedica(ActionEvent evento)
    {
        if (alertaEliminarFichaMédica())
        {
            FichaMedicaDAOImpl fichaMedicaDAO = new FichaMedicaDAOImpl();

            if (fichaMedicaDAO.deleteFichaPaciente(paciente))
            {
                alertaInfo();
                cargarVistaTablaPacientes();
            }
            else
                alertaError();
        }
    }

    @FXML
    void cargarVistaModificarPaciente(ActionEvent evento)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/FormularioModificarPaciente.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de TablaPacientes
            FormularioModificarPacienteController controlador = (FormularioModificarPacienteController) loader.getController();
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
    private boolean alertaEliminarPaciente()
    {
        Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);
        String contenido = "Paciente: "+paciente.getNombre()+"\nRut: "+paciente.getRut();

        ventana.setTitle("Confirmar Eliminación de Paciente");
        ventana.setHeaderText(contenido);
        ventana.initStyle(StageStyle.UTILITY);
        ventana.setContentText("¿Realmente desea eliminar este paciente?");


        Optional<ButtonType> opcion=ventana.showAndWait();

        if (opcion.get() == ButtonType.OK)
            return true;

        return false;
    }

    private boolean alertaEliminarFichaMédica()
    {
        Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);

        ventana.setTitle("Confirmar Eliminación de Ficha Médica");
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
        ventana.setHeaderText("Se ha eliminado de la base de datos satisfactioramente.");
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
