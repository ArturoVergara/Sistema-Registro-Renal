package controllers;

import DAO.PacienteDAOImpl;
import DAO.PersonalMedicoDAOImpl;
import DAO.UsuarioDAOImpl;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import models.Paciente;
import models.PersonalMedico;
import models.Usuario;
import models.enums.PersonalEnum;
import models.enums.PrevisionEnum;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class FormularioAgregarUsuarioController implements Initializable
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
    private JFXTextField direccion;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField telefono;
    @FXML
    private JFXPasswordField contrasena1;
    @FXML
    private JFXPasswordField contrasena2;
    @FXML
    private JFXComboBox<PersonalEnum> ocupacion;
    @FXML
    private Label errorNombre;
    @FXML
    private Label errorRut;
    @FXML
    private Label errorDireccion;
    @FXML
    private Label errorEmail;
    @FXML
    private Label errorTelefono;
    @FXML
    private Label errorContrasena1;
    @FXML
    private Label errorContrasena2;
    @FXML
    private Label errorOcupacion;

    private PersonalMedico usuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void inicializar(PersonalMedico usuario)
    {
        this.usuario = usuario;

        nombreUsuario.setText(usuario.getNombre());
        ocupacion.setItems(FXCollections.observableArrayList(PersonalEnum.ADMIN, PersonalEnum.DOCTOR, PersonalEnum.ENFERMERO, PersonalEnum.LABORATORISTA, PersonalEnum.GES));

        //StringConverter para el combobox de prevision sobre el enum de personal
        ocupacion.setConverter(new StringConverter<PersonalEnum>() {
            @Override
            public String toString(PersonalEnum objeto)
            {
                return objeto == null ? "" : objeto.getNombre();
            }

            @Override
            public PersonalEnum fromString(String string)
            {
                return PersonalEnum.valueOf(string);
            }
        });

        //Rut como los dioses
        rut.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                String temporalNuevo = newValue.replaceAll("[^\\d]", "");
                String temporalViejo = oldValue.replaceAll("[^\\d]", "");

                if (temporalNuevo.length() < 8)
                    rut.setText(String.format("%,d", Integer.parseInt(temporalNuevo)));
                else if (temporalNuevo.length() < 9)
                    rut.setText(String.format("%,d", Integer.parseInt(temporalNuevo.substring(0,7))) + "-" + temporalNuevo.substring(7));
                else if (temporalNuevo.length() < 10)
                    rut.setText(String.format("%,d", Integer.parseInt(temporalNuevo.substring(0,8))) + "-" + temporalNuevo.substring(8));
                else
                    rut.setText(temporalViejo);
            }
        });

        //Asignar al campo telefono la propiedad de aceptar solo un input númerico y con un signo mas antes (+)
        // Ejemplo: +56911223344
        telefono.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if (!newValue.matches("([\\+])?\\d{0,11}"))
                    telefono.setText(oldValue);
            }
        });
    }

    public boolean verificarRut(String rut)
    {
        byte[] serie = {2,3,4,5,6,7};
        int digito, suma = 0;

        rut = rut.replaceAll("[^\\d]", "");

        for (int i = rut.length() - 2, j = 0; i >= 0; i--, j++)
        {
            if (j > 5)
                j = 0;

            suma += Character.getNumericValue(rut.charAt(i)) * serie[j];
        }

        digito = 11 - (suma - (11 * (suma / 11)));
        return (digito == Character.getNumericValue(rut.charAt(rut.length() - 1)));
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
    private void cargarVistaMenuAdministrador()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/MenuAdministrador.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de TablaPacientes
            MenuAdministradorController controlador = (MenuAdministradorController) loader.getController();
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
    private void agregarUsuario(ActionEvent evento)
    {
        //Resetear todos los estilos
        nombre.setFocusColor(Color.rgb(106,114,239));
        errorNombre.setText("");
        rut.setFocusColor(Color.rgb(106,114,239));
        errorRut.setText("");
        direccion.setFocusColor(Color.rgb(106,114,239));
        errorDireccion.setText("");
        ocupacion.setFocusColor(Color.rgb(106,114,239));
        errorOcupacion.setText("");
        email.setFocusColor(Color.rgb(106,114,239));
        errorEmail.setText("");
        telefono.setFocusColor(Color.rgb(106,114,239));
        errorTelefono.setText("");
        contrasena1.setFocusColor(Color.rgb(106,114,239));
        errorContrasena1.setText("");
        contrasena2.setFocusColor(Color.rgb(106,114,239));
        errorContrasena2.setText("");


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
            errorRut.setText("Por favor ingrese un rut.");
            return;
        }

        if (!verificarRut(rut.getText()))
        {
            rut.requestFocus();
            rut.setFocusColor(Color.rgb(255,23,68));
            errorRut.setText("Por favor ingrese un rut válido.");
            return;
        }

        if (direccion.getText().isEmpty())
        {
            direccion.requestFocus();
            direccion.setFocusColor(Color.rgb(255,23,68));
            errorDireccion.setText("Por favor ingrese una dirección de domicilio.");
            return;
        }

        if (email.getText().isEmpty())
        {
            email.requestFocus();
            email.setFocusColor(Color.rgb(255,23,68));
            errorEmail.setText("Por favor ingrese una dirección de correo electrónico.");
            return;
        }

        if (!email.getText().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
        {
            email.requestFocus();
            email.setFocusColor(Color.rgb(255,23,68));
            errorEmail.setText("Por favor ingrese un email válido.");
            return;
        }

        if (telefono.getText().isEmpty())
        {
            telefono.requestFocus();
            telefono.setFocusColor(Color.rgb(255,23,68));
            errorTelefono.setText("Por favor ingrese un número de teléfono.");
            return;
        }

        if (contrasena1.getText().isEmpty())
        {
            contrasena1.requestFocus();
            contrasena1.setFocusColor(Color.rgb(255,23,68));
            errorContrasena1.setText("Por favor ingrese una contraseña.");
            return;
        }

        if (contrasena2.getText().isEmpty())
        {
            contrasena2.requestFocus();
            contrasena2.setFocusColor(Color.rgb(255,23,68));
            errorContrasena2.setText("Por favor confirme su contraseña");
            return;
        }

        if (ocupacion.getValue() == null)
        {
            ocupacion.requestFocus();
            ocupacion.setFocusColor(Color.rgb(255,23,68));
            errorOcupacion.setText("Por favor seleccione un tipo de ocupación.");
            return;
        }

        if (contrasena1.getText().compareTo(contrasena2.getText()) != 0)
        {
            contrasena1.requestFocus();
            contrasena1.setFocusColor(Color.rgb(255,23,68));
            errorContrasena1.setText("Las contraseñas no coinciden.");
            return;
        }

        PersonalMedicoDAOImpl personalMedicoDAO = new PersonalMedicoDAOImpl();

        if (personalMedicoDAO.getPersonalMedico(rut.getText()) != null)
        {
            alertaError("Ya existe un usuario registrado con ese rut en el sistema.");
            rut.requestFocus();
            rut.setFocusColor(Color.rgb(255,23,68));
            return;
        }

        PersonalMedico dato = new PersonalMedico(
                rut.getText(),
                nombre.getText(),
                contrasena1.getText(),
                direccion.getText(),
                email.getText(),
                telefono.getText(),
                ocupacion.getValue()
        );

        if (personalMedicoDAO.createPersonalMedico(dato) != null)
        {
            alertaInfo();
            cargarVistaMenuAdministrador();
        }
        else
            alertaError("Ocurrió un error al ingresar el usuario en la base de datos.");
    }

    private void alertaInfo()
    {
        Alert ventana=new Alert(Alert.AlertType.INFORMATION);
        ventana.setTitle("¡Éxito al ingresar!");
        ventana.setHeaderText("Se ha ingresado el usuario satisfactioramente.");
        ventana.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        ventana.showAndWait();
    }

    //Muestra un cuadro de dialogo de error, con un mensaje del porqué ocurrió dicho error
    private void alertaError(String mensaje)
    {
        Alert ventana=new Alert(Alert.AlertType.ERROR);
        ventana.setTitle("¡Error al ingresar!");
        ventana.setHeaderText("Error: No se pudo ingresar el usuario");
        ventana.setContentText(mensaje);
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
