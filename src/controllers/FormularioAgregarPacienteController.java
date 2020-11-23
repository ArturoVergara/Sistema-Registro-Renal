package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import models.Paciente;
import models.PersonalMedico;
import models.enums.PrevisionEnum;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class FormularioAgregarPacienteController implements Initializable
{
    @FXML
    private BorderPane parentContainer;
    @FXML
    private JFXTextField nombre;
    @FXML
    private JFXTextField rut;
    @FXML
    private JFXDatePicker fechaNacimiento;
    @FXML
    private JFXTextField direccion;
    @FXML
    private JFXComboBox<PrevisionEnum> prevision;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField telefono;
    @FXML
    private Label errorNombre;
    @FXML
    private Label errorRut;
    @FXML
    private Label errorFechaNacimiento;
    @FXML
    private Label errorDireccion;
    @FXML
    private Label errorPrevision;
    @FXML
    private Label errorEmail;
    @FXML
    private Label errorTelefono;

    private PersonalMedico usuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void inicializar(PersonalMedico usuario)
    {
        this.usuario = usuario;

        prevision.setItems(FXCollections.observableArrayList(PrevisionEnum.FONASA, PrevisionEnum.ISAPRE, PrevisionEnum.CAPREDENA));

        System.out.println(PrevisionEnum.FONASA.getValor());

        prevision.setConverter(new StringConverter<PrevisionEnum>() {
            @Override
            public String toString(PrevisionEnum objeto)
            {
                return objeto == null ? "" : objeto.getNombre();
            }

            @Override
            public PrevisionEnum fromString(String string)
            {
                return PrevisionEnum.valueOf(string);
            }
        });
    }

    @FXML
    private void agregarPaciente(ActionEvent evento)
    {
        //Resetear todos los estilos
        nombre.setFocusColor(Color.rgb(106,114,239));
        errorNombre.setText("");
        rut.setFocusColor(Color.rgb(106,114,239));
        errorRut.setText("");
        fechaNacimiento.setDefaultColor(Color.rgb(106,114,239));
        errorFechaNacimiento.setText("");
        direccion.setFocusColor(Color.rgb(106,114,239));
        errorDireccion.setText("");
        prevision.setFocusColor(Color.rgb(106,114,239));
        errorPrevision.setText("");
        email.setFocusColor(Color.rgb(106,114,239));
        errorEmail.setText("");
        telefono.setFocusColor(Color.rgb(106,114,239));
        errorTelefono.setText("");

        if (nombre.getText().isEmpty())
        {
            nombre.requestFocus();
            nombre.setFocusColor(Color.rgb(255,23,68));
            errorNombre.setText("Por favor ingrese un nombre.");
            return;
        }

        if (rut.getText().isEmpty())
        {
            rut.requestFocus();
            rut.setFocusColor(Color.rgb(255,23,68));
            errorRut.setText("Por favor ingrese un rut válido.");
            return;
        }

        if (fechaNacimiento.getValue() == null)
        {
            fechaNacimiento.requestFocus();
            fechaNacimiento.setDefaultColor(Color.rgb(255,23,68));
            errorFechaNacimiento.setText("Por favor ingrese una fecha de nacimiento.");
            return;
        }

        //Verifica si los datos ingresados coinciden con una cuenta
        if (direccion.getText().isEmpty())
        {
            direccion.requestFocus();
            direccion.setFocusColor(Color.rgb(255,23,68));
            errorDireccion.setText("Por favor ingrese una dirección de domicilio.");
            return;
        }

        if (prevision.getValue() == null)
        {
            prevision.requestFocus();
            prevision.setFocusColor(Color.rgb(255,23,68));
            errorPrevision.setText("Por favor seleccione una previsión médica.");
            return;
        }

        //Verifica si los datos ingresados coinciden con una cuenta
        if (email.getText().isEmpty())
        {
            email.requestFocus();
            email.setFocusColor(Color.rgb(255,23,68));
            errorEmail.setText("Por favor ingrese una dirección de correo electrónico.");
            return;
        }

        //Verifica si los datos ingresados coinciden con una cuenta
        if (telefono.getText().isEmpty())
        {
            telefono.requestFocus();
            telefono.setFocusColor(Color.rgb(255,23,68));
            errorTelefono.setText("Por favor ingrese un número de teléfono.");
            return;
        }

        Paciente dato = new Paciente(
                rut.getText(),
                nombre.getText(),
                direccion.getText(),
                email.getText(),
                telefono.getText(),
                Date.from(fechaNacimiento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                prevision.getValue(),
                "Chilena"
        );

        System.out.println(dato.toString());
    }
}
