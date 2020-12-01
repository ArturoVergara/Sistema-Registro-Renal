package controllers;

import DAO.PersonalMedicoDAOImpl;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.PersonalMedico;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TablaUsuariosController implements Initializable
{
    @FXML
    private BorderPane parentContainer;
    @FXML
    private Label nombreUsuario;
    @FXML
    private TableView<PersonalMedico> tabla;
    @FXML
    private TableColumn<PersonalMedico, String> columnaNombre;
    @FXML
    private TableColumn<PersonalMedico, String> columnaRut;
    @FXML
    private TableColumn<PersonalMedico, String> columnaAcciones;

    private PersonalMedico usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void inicializar(PersonalMedico usuario)
    {
        this.usuario = usuario;
        nombreUsuario.setText(usuario.getNombre());

        PersonalMedicoDAOImpl personalMedicoDAO = new PersonalMedicoDAOImpl();
        List<PersonalMedico> usuarios = personalMedicoDAO.getAllPersonalMedico();

        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaRut.setCellValueFactory(new PropertyValueFactory<>("rut"));
        columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("rut"));

        //Callback para reemplazar el valor de la columna Acciones por los botones agregar, modificar y eliminar
        Callback<TableColumn<PersonalMedico, String>, TableCell<PersonalMedico, String>> cellFactory =  new Callback<TableColumn<PersonalMedico, String>, TableCell<PersonalMedico, String>>()
        {
            @Override
            public TableCell call(TableColumn<PersonalMedico, String> param)
            {
                TableCell<PersonalMedico, String> cell = new TableCell<PersonalMedico, String>()
                {
                    @Override
                    public void updateItem(String item, boolean empty)
                    {
                        JFXButton botonVer = new JFXButton("Ver");
                        JFXButton botonModificar = new JFXButton("Modificar");
                        JFXButton botonEliminar = new JFXButton("Eliminar");
                        HBox hbox = new HBox();

                        super.updateItem(item, empty);

                        if (empty)
                        {
                            setGraphic(null);
                            setText(null);
                        }
                        else
                        {
                            botonVer.setOnAction(event -> {
                                PersonalMedico dato = getTableView().getItems().get(getIndex());
                                cargarVistaPerfilUsuario(dato);
                            });

                            botonModificar.setOnAction(event -> {
                                PersonalMedico dato = getTableView().getItems().get(getIndex());
                                cargarVistaModificarUsuario(dato);
                            });

                            botonEliminar.setOnAction(event -> {
                                PersonalMedico dato = getTableView().getItems().get(getIndex());
                                eliminarUsuario(dato, getIndex());
                            });

                            hbox.setSpacing(10.0);

                            //Setear estilos a los botones
                            botonVer.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white");
                            botonModificar.setStyle("-fx-background-color: #ffab00; -fx-text-fill: white");
                            botonEliminar.setStyle("-fx-background-color: #ff1744; -fx-text-fill: white");
                            botonVer.setCursor(Cursor.HAND);
                            botonModificar.setCursor(Cursor.HAND);
                            botonEliminar.setCursor(Cursor.HAND);

                            //Deshabilita el boton eliminar si es del mismo usuario logueado
                            if (getTableView().getItems().get(getIndex()).getId() == usuario.getId())
                                botonEliminar.setDisable(true);

                            hbox.getChildren().addAll(botonVer, botonModificar, botonEliminar);
                            setGraphic(hbox);
                            setText(null);
                        }
                    }
                };

                return cell;
            }
        };

        columnaAcciones.setCellFactory(cellFactory);
        tabla.setItems(FXCollections.observableArrayList(usuarios));
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

            //Obtiene el controlador de Login
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

    void cargarVistaPerfilUsuario(PersonalMedico dato)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/PerfilUsuario.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de PerfilUsuario
            PerfilUsuarioController controlador = (PerfilUsuarioController) loader.getController();
            controlador.inicializar(usuario, dato);

            Stage ventana = (Stage) parentContainer.getScene().getWindow();
            ventana.setScene(escena);
            ventana.show();
        }
        catch (IOException | IllegalStateException excepcion)
        {
            alertaExcepcion(excepcion);
        }
    }

    void cargarVistaModificarUsuario(PersonalMedico dato)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/FormularioModificarUsuario.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de FormularioModificarUsuario
            FormularioModificarUsuarioController controlador = (FormularioModificarUsuarioController) loader.getController();
            controlador.inicializar(usuario, dato);

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

            //Obtiene el controlador de MenuAdministrador
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

    void eliminarUsuario(PersonalMedico dato, int indice)
    {
        if (alertaEliminar(dato))
        {
            PersonalMedicoDAOImpl personalMedicoDAO = new PersonalMedicoDAOImpl();

            if (personalMedicoDAO.deletePersonalMedico(dato.getId()))
            {
                tabla.getItems().remove(indice);
                alertaInfo();
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

    //Muestra un cuadro de dialogo, donde pide confirmación para eliminar el usuario, retornando un booleano
    private boolean alertaEliminar(PersonalMedico dato)
    {
        Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);
        String contenido = "Usuario: "+dato.getNombre()+"\nRut: "+dato.getRut();

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
