package controllers;

import DAO.PersonalMedicoDAOImpl;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.PersonalMedico;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PerfilUsuarioController implements Initializable
{
    @FXML
    private BorderPane parentContainer;
    @FXML
    private Label nombreUsuario;
    @FXML
    private Label nombre;
    @FXML
    private Label rut;
    @FXML
    private Label fechaCreacion;
    @FXML
    private Label direccion;
    @FXML
    private Label ocupacion;
    @FXML
    private Label email;
    @FXML
    private Label telefono;
    @FXML
    private JFXButton botonModificar;
    @FXML
    private JFXButton botonEliminar;

    private PersonalMedico usuario;
    private PersonalMedico perfil;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void inicializar(PersonalMedico usuario, PersonalMedico perfil)
    {
        this.usuario = usuario;
        this.perfil = perfil;
        nombreUsuario.setText(usuario.getNombre());

        //System.out.println(paciente.getFichaPaciente());

        this.nombre.setText(perfil.getNombre());
        this.rut.setText(perfil.getRut());
        //this.fechaCreacion.setText(new SimpleDateFormat("dd / MM / yyyy").format(usuario.getFechaCreacion()));
        this.direccion.setText(perfil.getDireccion());
        this.email.setText(perfil.getEmail());
        this.telefono.setText(perfil.getTelefono());

        String ocupacion = "";

        switch (perfil.getTipoPersonal())
        {
            case ADMIN:
                ocupacion = "Administrador";
                break;

            case DOCTOR:
                ocupacion = "Doctor";
                break;

            case LABORATORISTA:
                ocupacion = "Laboratorista";
                break;

            default:
                ocupacion = "Enfermero";
        }

        this.ocupacion.setText(ocupacion);

        if (usuario.isAdmin())
        {
            botonModificar.setVisible(true);
            botonModificar.setDisable(false);

            if (usuario.getId() != perfil.getId())
            {
                botonEliminar.setVisible(true);
                botonEliminar.setDisable(false);
            }
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
    private void cargaraVistaVentanaAnterior()
    {
        if (usuario.isAdmin())
            cargarVistaMenuAdministrador();
        else
            cargarVistaMenuPrincipal();
    }

    private void cargarVistaMenuPrincipal()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/MenuPrincipal.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de MenuPrincipal
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

    @FXML
    private void cargarVistaModificarUsuario()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/FormularioModificarUsuario.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de FormularioModificarUsuario
            FormularioModificarUsuarioController controlador = (FormularioModificarUsuarioController) loader.getController();
            controlador.inicializar(usuario, perfil);

            Stage ventana = (Stage) parentContainer.getScene().getWindow();
            ventana.setScene(escena);
            ventana.show();
        }
        catch (IOException | IllegalStateException excepcion)
        {
            alertaExcepcion(excepcion);
        }
    }

    private void cargarVistaMenuAdministrador()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/MenuAdministrador.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de MenuPrincipal
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
    private void cambiarContrasena()
    {
        Dialog<String> ventana = new Dialog<>();

        ventana.setTitle("Cambiar Contraseña");
        ventana.setHeaderText("Por favor ingrese los siguientes campos para cambiar su contraseña.");

        ButtonType botonCrear = new ButtonType("Cambiar", ButtonBar.ButtonData.OK_DONE);
        ventana.getDialogPane().getButtonTypes().addAll(botonCrear, ButtonType.CANCEL);

        GridPane grid = new GridPane();

        grid.setHgap(15);
        grid.setVgap(15);

        PasswordField contrasena1 = new PasswordField();
        PasswordField contrasena2 = new PasswordField();

        grid.add(new Label("Contraseña: "), 0, 0);
        grid.add(contrasena1,1,0);
        grid.add(new Label("Confirmar Contraseña:"),0,1);
        grid.add(contrasena2,1,1);

        ventana.getDialogPane().setContent(grid);

        ventana.setResultConverter(botonDialogo -> {
            if (botonDialogo == botonCrear)
            {
                if (contrasena1.getText().compareTo(contrasena2.getText()) != 0)
                {
                    alertaErrorContrasena("Las contraseñas no coinciden");
                    return null;
                }
                if (contrasena1.getText().length() < 8)
                {
                    alertaErrorContrasena("Las contraseñas deben tener un largo mínimo de 8 caracteres");
                    return null;
                }

                return contrasena1.getText();
            }

            return  null;
        });

        Optional<String> resultadoVentana = ventana.showAndWait();

        if (resultadoVentana.isPresent())
        {
            PersonalMedicoDAOImpl personalMedicoDAO = new PersonalMedicoDAOImpl();

            String contrasenaAntigua = perfil.getContrasena();
            perfil.setContrasena(resultadoVentana.get());

            if (personalMedicoDAO.updateContrasena(perfil))
            {
               alertaInfoContrasena();
            }
            else
            {
                alertaErrorContrasena("Error al actualizar la contraseña en la base de datos");
                perfil.setContrasena(contrasenaAntigua);
            }
        }
    }

    @FXML
    private void eliminarUsuario()
    {
        if (alertaEliminar())
        {
            PersonalMedicoDAOImpl personalMedicoDAO = new PersonalMedicoDAOImpl();

            if (personalMedicoDAO.deletePersonalMedico(perfil.getId()))
            {
                alertaInfo();
                cargaraVistaVentanaAnterior();
            }
            else
                alertaError();
        }
    }


    private void alertaError()
    {
        Alert ventana=new Alert(Alert.AlertType.ERROR);
        ventana.setTitle("¡Error al eliminar!");
        ventana.setHeaderText("Error: No se pudo eliminar el usuario");
        ventana.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        ventana.showAndWait();
    }

    private void alertaErrorContrasena(String mensaje)
    {
        Alert ventana=new Alert(Alert.AlertType.ERROR);
        ventana.setTitle("¡Error al cambiar contraseña!");
        ventana.setHeaderText("Error: No se pudo cambiar la contraseña del usuario");
        ventana.setContentText(mensaje);
        ventana.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        ventana.showAndWait();
    }

    //Muestra un cuadro de dialogo, donde pide confirmación para eliminar el usuario, retornando un booleano
    private boolean alertaEliminar()
    {
        Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);
        String contenido = "Usuario: "+perfil.getNombre()+"\nRut: "+perfil.getRut();

        ventana.setTitle("Confirmar Eliminación de Usuario");
        ventana.setHeaderText(contenido);
        ventana.initStyle(StageStyle.UTILITY);
        ventana.setContentText("¿Realmente desea eliminar este usuario?");


        Optional<ButtonType> opcion=ventana.showAndWait();

        if (opcion.get() == ButtonType.OK)
            return true;

        return false;
    }

    private void alertaInfo()
    {
        Alert ventana=new Alert(Alert.AlertType.INFORMATION);
        ventana.setTitle("¡Éxito al eliminar!");
        ventana.setHeaderText("Se ha eliminado al usuario satisfactioramente.");
        ventana.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        ventana.showAndWait();
    }

    private void alertaInfoContrasena()
    {
        Alert ventana=new Alert(Alert.AlertType.INFORMATION);
        ventana.setTitle("¡Éxito al cambiar la contraseña!");
        ventana.setHeaderText("Se ha cambiado la contraseña del usuario satisfactioramente.");
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
