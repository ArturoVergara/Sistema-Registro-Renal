package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private StackPane parentContainer;
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
        //Verifica si los datos ingresados coinciden con una cuenta
        /*
        if (!Usuario.iniciarSesion(usuario.getText(),contraseña.getText()))
        {
            advertencia.setText("Los datos ingresados no son válidos.");
            return;
        }*/

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

        if ((rut.getText().compareTo("admin") == 0) && (contrasenia.getText().compareTo("admin") == 0))
            //cargarMenuPrincipal();
            System.out.println("Datos Correctos");
        else
            labelContrasenia.setText("Las credenciales no son correctas.");
    }


    //Carga la ventana del MenuPrincipal con una animación de barrido horizontal
    private void cargarMenuPrincipal()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MenuPrincipal.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = anchorRoot.getScene();

            //Obtiene el controlador de MenuAdministrador para inicializar la clase Cajero y enviarla por parámetros
            MenuPrincipalController controlador = (MenuPrincipalController) loader.getController();
            //controlador.inicializar(new Cajero(usuario.getText()));

            //Obtiene el ancho y largo del panel para realizar la transición horizontal hacia la siguiente escena
            root.translateXProperty().set(scene.getWidth());
            parentContainer.getChildren().add(root);
            Timeline timeline = new Timeline();
            KeyValue value = new KeyValue(root.translateXProperty(),0, Interpolator.EASE_IN);
            KeyFrame frame = new KeyFrame(Duration.seconds(1),value);
            timeline.getKeyFrames().add(frame);
            timeline.setOnFinished((ActionEvent evento) -> {
                parentContainer.getChildren().remove(anchorRoot);
            });
            timeline.play();
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
