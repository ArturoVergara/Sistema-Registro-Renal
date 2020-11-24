package controllers;

import DAO.FichaMedicaDAO;
import DAO.FichaMedicaDAOImpl;
import DAO.PacienteDAOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import models.FichaMedica;
import models.Paciente;
import models.PersonalMedico;
import models.enums.PrevisionEnum;
import sun.font.CharToGlyphMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class FormularioAgregarFichaMedicaController implements Initializable
{
    @FXML
    private BorderPane parentContainer;
    @FXML
    private Label nombreUsuario;
    @FXML
    private JFXTextField nombre;
    @FXML
    private JFXTextField rut;
    @FXML
    private ToggleGroup sexo;
    @FXML
    private JFXRadioButton masculino;
    @FXML
    private JFXRadioButton femenino;
    @FXML
    private ToggleGroup etnia;
    @FXML
    private JFXRadioButton blanca;
    @FXML
    private JFXRadioButton negra;
    @FXML
    private JFXTextField estatura;
    @FXML
    private JFXTextField peso;
    @FXML
    private Label errorSexo;
    @FXML
    private Label errorEtnia;
    @FXML
    private Label errorEstatura;
    @FXML
    private Label errorPeso;

    private PersonalMedico usuario;
    private Paciente paciente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void inicializar(PersonalMedico usuario, Paciente paciente)
    {
        this.usuario = usuario;
        this.paciente = paciente;

        nombreUsuario.setText(usuario.getNombre());
        nombre.setText(paciente.getNombre());
        rut.setText(paciente.getRut());
    }

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
    private void agregarFichaMedica(ActionEvent evento)
    {
        //Resetear todos los estilos
        errorEstatura.setText("");
        errorEtnia.setText("");
        errorPeso.setText("");
        errorSexo.setText("");

        if (sexo.getSelectedToggle() == null)
        {
            masculino.requestFocus();
            errorSexo.setText("Por favor seleccione una opción.");
            return;
        }

        if (etnia.getSelectedToggle() == null)
        {
            blanca.requestFocus();
            errorEtnia.setText("Por favor seleccione una opción.");
            return;
        }

        if (estatura.getText().isEmpty())
        {
            estatura.requestFocus();
            estatura.setFocusColor(Color.rgb(255,23,68));
            errorEstatura.setText("Por favor ingrese una estatura.");
            return;
        }

        if (peso.getText().isEmpty())
        {
            peso.requestFocus();
            peso.setFocusColor(Color.rgb(255,23,68));
            errorPeso.setText("Por favor ingrese un peso.");
            return;
        }

        String etniaString;

        if (blanca.isSelected())
            etniaString = "Blanca";
        else
            etniaString = "Negra";

        //Agregar la ficha médica en la base de datos
        FichaMedica dato = new FichaMedica(
                masculino.isSelected(),
                Float.parseFloat(peso.getText()),
                Float.parseFloat(estatura.getText()),
                etniaString
        );

        FichaMedicaDAOImpl fichaMedicaDAO = new FichaMedicaDAOImpl();

        //fichaMedicaDAO.
    }

    private void alertaInfo()
    {
        Alert ventana=new Alert(Alert.AlertType.INFORMATION);
        ventana.setTitle("¡Éxito al ingresar!");
        ventana.setHeaderText("Se ha agregado la ficha médica al paciente correctamente.");
        ventana.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        ventana.showAndWait();
    }

    //Muestra un cuadro de dialogo de error, con un mensaje del porqué ocurrió dicho error
    private void alertaError()
    {
        Alert ventana=new Alert(Alert.AlertType.ERROR);
        ventana.setTitle("¡Error al ingresar!");
        ventana.setHeaderText("Error: No se pudo agregar la ficha médica");
        ventana.setContentText("Ocurrió un error al ingresar la ficha médica en la base de datos.");
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
