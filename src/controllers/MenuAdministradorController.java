package controllers;

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
import javafx.stage.Stage;
import models.PersonalMedico;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuAdministradorController implements Initializable
{
    @FXML
    private BorderPane parentContainer;
    @FXML
    private Label nombreUsuario;

    private PersonalMedico usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void inicializar(PersonalMedico usuario)
    {
        this.usuario = usuario;
        nombreUsuario.setText(usuario.getNombre());
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
    private void cargarVistaPerfilUsuario()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/PerfilUsuario.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de PerfilUsuario
            PerfilUsuarioController controlador = (PerfilUsuarioController) loader.getController();
            controlador.inicializar(usuario, usuario);

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
    private void cargarVistaAgregarUsuario()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/FormularioAgregarUsuario.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de TablaPacientes
            FormularioAgregarUsuarioController controlador = (FormularioAgregarUsuarioController) loader.getController();
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
    private void cargarVistaTablaUsuarios()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/TablaUsuarios.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de PerfilUsuario
            TablaUsuariosController controlador = (TablaUsuariosController) loader.getController();
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
