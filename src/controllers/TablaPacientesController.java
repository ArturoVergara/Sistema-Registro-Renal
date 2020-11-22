package controllers;

import DAO.PacienteDAOImpl;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.Paciente;
import models.PersonalMedico;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TablaPacientesController implements Initializable
{
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
                                cargarVistaVerPaciente(dato);
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

    void cargarVistaVerPaciente(Paciente dato)
    {

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
}
