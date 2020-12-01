package controllers;

import DAO.PersonalMedicoDAOImpl;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import models.PersonalMedico;
import models.enums.PersonalEnum;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class FormularioModificarUsuarioController implements Initializable
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
    private Label errorOcupacion;

    private PersonalMedico usuario;
    private PersonalMedico perfil;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void inicializar(PersonalMedico usuario, PersonalMedico perfil)
    {
        this.usuario = usuario;
        this.perfil = perfil;

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

        //Se setean todos los atributos
        nombre.setText(perfil.getNombre());
        rut.setText(perfil.getRut());
        email.setText(perfil.getEmail());
        direccion.setText(perfil.getDireccion());
        telefono.setText(perfil.getTelefono());
        ocupacion.getSelectionModel().select(perfil.getTipoPersonal());
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

            //Agregar rut con K
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
    private void modificarUsuario(ActionEvent evento)
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

        if (ocupacion.getValue() == null)
        {
            ocupacion.requestFocus();
            ocupacion.setFocusColor(Color.rgb(255,23,68));
            errorOcupacion.setText("Por favor seleccione un tipo de ocupación.");
            return;
        }

        //Se ingresa a la base de datos
        PersonalMedicoDAOImpl personalMedicoDAO = new PersonalMedicoDAOImpl();

        //Si se modifico el rut, verifica que no exista otro igual en el sistema
        if ((perfil.getRut().compareTo(rut.getText()) != 0) && (personalMedicoDAO.getPersonalMedico(rut.getText()) != null))
        {
            alertaError("Ya existe un usuario registrado con ese rut en el sistema.");
            rut.requestFocus();
            rut.setFocusColor(Color.rgb(255,23,68));
            return;
        }

        perfil.setNombre(nombre.getText());
        perfil.setRut(rut.getText());
        perfil.setDireccion(direccion.getText());
        perfil.setEmail(email.getText());
        perfil.setTelefono(telefono.getText());
        perfil.setTipoPersonal(ocupacion.getValue());


        if (personalMedicoDAO.updatePersonalMedico(perfil) != null)
        {
            alertaInfo();
            cargarVistaMenuAdministrador();
        }
        else
            alertaError("Ocurrió un error al modificar el usuario en la base de datos.");
    }

    private void alertaInfo()
    {
        Alert ventana=new Alert(Alert.AlertType.INFORMATION);
        ventana.setTitle("¡Éxito al modificar!");
        ventana.setHeaderText("Se ha modificado el usuario satisfactioramente.");
        ventana.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        ventana.showAndWait();
    }

    //Muestra un cuadro de dialogo de error, con un mensaje del porqué ocurrió dicho error
    private void alertaError(String mensaje)
    {
        Alert ventana=new Alert(Alert.AlertType.ERROR);
        ventana.setTitle("¡Error al modificar!");
        ventana.setHeaderText("Error: No se pudo modificar el usuario");
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
