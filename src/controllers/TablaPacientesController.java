package controllers;

import DAO.PacienteDAOImpl;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
import models.Paciente;
import models.PersonalMedico;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TablaPacientesController implements Initializable
{
    @FXML
    private BorderPane parentContainer;
    @FXML
    private TableView<Paciente> tabla;
    @FXML
    private TableColumn<Paciente, String> columnaNombre;
    @FXML
    private TableColumn<Paciente, String> columnaRut;
    @FXML
    private TableColumn<Paciente, String> columnaAcciones;

    private PersonalMedico usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void inicializar(PersonalMedico usuario)
    {
        this.usuario = usuario;
        PacienteDAOImpl pacienteDAO = new PacienteDAOImpl();
        List<Paciente> pacientes = pacienteDAO.getPacientes();

        System.out.println(pacientes);

        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaRut.setCellValueFactory(new PropertyValueFactory<>("rut"));
        columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("rut"));

        //Callback para reemplazar el valor de la columna Acciones por los botones agregar, modificar y eliminar
        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> cellFactory =  new Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>>()
        {
            @Override
            public TableCell call(TableColumn<Paciente, String> param)
            {
                TableCell<Paciente, String> cell = new TableCell<Paciente, String>()
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
                                Paciente dato = getTableView().getItems().get(getIndex());
                                cargarVistaPerfilPaciente(dato);
                            });

                            botonModificar.setOnAction(event -> {
                                Paciente dato = getTableView().getItems().get(getIndex());
                                cargarVistaModificarPaciente(dato);
                            });

                            botonEliminar.setOnAction(event -> {
                                Paciente dato = getTableView().getItems().get(getIndex());
                                eliminarPaciente(dato, getIndex());
                            });

                            hbox.setSpacing(10.0);

                            //Setear estilos a los botones
                            botonVer.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white");
                            botonModificar.setStyle("-fx-background-color: #ffab00; -fx-text-fill: white");
                            botonEliminar.setStyle("-fx-background-color: #ff1744; -fx-text-fill: white");
                            botonVer.setCursor(Cursor.HAND);
                            botonModificar.setCursor(Cursor.HAND);
                            botonEliminar.setCursor(Cursor.HAND);

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
        tabla.setItems(FXCollections.observableArrayList(pacientes));
    }

    void cargarVistaPerfilPaciente(Paciente dato)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/PerfilPaciente.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de TablaPacientes
            PerfilPacienteController controlador = (PerfilPacienteController) loader.getController();
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

    void cargarVistaModificarPaciente(Paciente dato)
    {

    }

    void eliminarPaciente(Paciente dato, int indice)
    {
        if (alertaEliminar(dato))
        {
            tabla.getItems().remove(indice);
            //Falta eliminar de la base de datos
        }
    }

    //Muestra un cuadro de dialogo, donde pide confirmación para eliminar el paciente, retornando un booleano
    private boolean alertaEliminar(Paciente dato)
    {
        Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);
        String contenido = "Paciente: "+dato.getNombre()+"\nRut: "+dato.getRut();

        ventana.setTitle("Confirmar Eliminación de Paciente");
        ventana.setHeaderText(contenido);
        ventana.initStyle(StageStyle.UTILITY);
        ventana.setContentText("¿Realmente desea eliminar este paciente?");


        Optional<ButtonType> opcion=ventana.showAndWait();

        if (opcion.get() == ButtonType.OK)
            return true;

        return false;
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
