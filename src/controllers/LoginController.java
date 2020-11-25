package controllers;

import DAO.PersonalMedicoDAO;
import DAO.PersonalMedicoDAOImpl;
import DAO.UsuarioDAOImpl;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.PersonalMedico;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    @FXML
    private SplitPane parentContainer;
    @FXML
    private JFXTextField rut;
    @FXML
    private JFXPasswordField contrasenia;
    @FXML
    private Label labelRut;
    @FXML
    private Label labelContrasenia;



    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        rut.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue)
            {
                //Verificar si es un rut valido
                if (!rut.getText().matches("^(\\d{1,3}(\\.?\\d{3})*)\\-?([\\dkK])$"))
                {
                    rut.requestFocus();
                    rut.setFocusColor(Color.rgb(255,23,68));
                    labelRut.setText("Por favor ingrese un rut válido.");
                }
                else
                {
                    rut.setFocusColor(Color.rgb(106,114,239));
                    labelRut.setText("");
                }
            }
        });

        contrasenia.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue)
            {
                if (!contrasenia.getText().isEmpty())
                {
                    contrasenia.setFocusColor(Color.rgb(106,114,239));
                    labelContrasenia.setText("");
                }
            }
        });
    }

    @FXML
    private void verificarDatos(ActionEvent evento)
    {
        labelContrasenia.setText("");

        //Verifica si los datos ingresados coinciden con una cuenta
        if (rut.getText().isEmpty())
        {
            rut.requestFocus();
            rut.setFocusColor(Color.rgb(255,23,68));
            labelRut.setText("Por favor ingrese un rut válido.");
            return;
        }

        if (contrasenia.getText().isEmpty())
        {
            contrasenia.requestFocus();
            contrasenia.setFocusColor(Color.rgb(255,23,68));
            labelContrasenia.setText("Por favor ingrese una contraseña.");
            return;
        }

        UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();

        if (usuarioDAO.testCredentialsPersonal(rut.getText(), contrasenia.getText()))
        {
            PersonalMedicoDAOImpl personalMedicoDAO = new PersonalMedicoDAOImpl();
            PersonalMedico usuario = personalMedicoDAO.getPersonalMedico(rut.getText());

            if (usuario.isAdmin())
                cargarMenuAdministrador(usuario);
            else
                cargarMenuPrincipal(usuario);
        }
        else
            labelContrasenia.setText("Las credenciales no son correctas.");

    }

    @FXML
    private void cargarMenuAdministrador(PersonalMedico usuario)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/MenuAdministrador.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador del MenuPrincipal
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
    private void cargarMenuPrincipal(PersonalMedico usuario)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/MenuPrincipal.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador del MenuPrincipal
            MenuPrincipalController controlador = (MenuPrincipalController) loader.getController();
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

    //Muestra una alerta con toda la información detallada de la excepción
    private void alertaExcepcion(Exception excepcion)
    {
        Alert alerta = new Alert(Alert.AlertType.ERROR);

        alerta.setTitle("Alerta Excepción");
        alerta.setHeaderText(excepcion.getMessage());
        alerta.setContentText(excepcion.toString());

        //Se imprime todo el stacktrace de la excepción en un cajón expandible de texto
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
