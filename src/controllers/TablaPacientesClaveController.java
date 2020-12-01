package controllers;

import DAO.PacienteDAOImpl;
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
import models.Paciente;
import models.PersonalMedico;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class TablaPacientesClaveController implements Initializable
{
    @FXML
    private BorderPane parentContainer;
    @FXML
    private Label nombreUsuario;
    @FXML
    private TableView<Paciente> tabla;
    @FXML
    private TableColumn<Paciente, String> columnaNombre;
    @FXML
    private TableColumn<Paciente, String> columnaRut;
    @FXML
    private TableColumn<Paciente, String> columnaClave;
    @FXML
    private TableColumn<Paciente, String> columnaAccion;

    private PersonalMedico usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void inicializar(PersonalMedico usuario)
    {
        this.usuario = usuario;
        nombreUsuario.setText(usuario.getNombre());

        PacienteDAOImpl pacienteDAO = new PacienteDAOImpl();
        List<Paciente> pacientes = pacienteDAO.getPacientes();

        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaRut.setCellValueFactory(new PropertyValueFactory<>("rut"));
        columnaClave.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        columnaAccion.setCellValueFactory(new PropertyValueFactory<>("rut"));

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
                        JFXButton botonGenerar = new JFXButton("Generar Clave");

                        super.updateItem(item, empty);

                        if (empty)
                        {
                            setGraphic(null);
                            setText(null);
                        }
                        else
                        {
                            botonGenerar.setOnAction(event -> {
                                Paciente dato = getTableView().getItems().get(getIndex());
                                generarClave(dato, getIndex());
                            });

                            //Setear estilos a los botones
                            botonGenerar.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white");
                            botonGenerar.setCursor(Cursor.HAND);
                            setGraphic(botonGenerar);
                            setText(null);
                        }
                    }
                };

                return cell;
            }
        };

        //Callback para reemplazar el valor de la columna Clave por Si o No
        Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>> cellFactoryClave =  new Callback<TableColumn<Paciente, String>, TableCell<Paciente, String>>()
        {
            @Override
            public TableCell call(TableColumn<Paciente, String> param)
            {
                TableCell<Paciente, String> cell = new TableCell<Paciente, String>()
                {
                    @Override
                    public void updateItem(String item, boolean empty)
                    {
                        super.updateItem(item, empty);

                        if (empty)
                        {
                            setGraphic(null);
                            setText(null);
                        }
                        else
                        {
                            if (item != null)
                                setText("Sí");
                            else
                                setText("No");

                            setGraphic(null);
                        }
                    }
                };

                return cell;
            }
        };

        columnaClave.setCellFactory(cellFactoryClave);
        columnaAccion.setCellFactory(cellFactory);
        tabla.setItems(FXCollections.observableArrayList(pacientes));
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
    private void cargarMenuPrincipal()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/MenuPrincipal.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de TablaPacientes
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

    private void generarClave(Paciente dato, int indice)
    {
        if (alertaConfirmar(dato))
        {
            PacienteDAOImpl pacienteDAO = new PacienteDAOImpl();
            String random = generateRandomPassword();

            dato.setContrasena(random);

            if (pacienteDAO.updatePaciente(dato) != null)
            {
                tabla.getItems().set(indice, dato);
                alertaInfo(random);
            }

            else
                alertaError();
        }
    }

    //Funcion para generar un string numerico aleatorio de largo 8
    private String generateRandomPassword()
    {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer(8);
        int caracterRandom;

        for (byte i = 0; i < 8 ; i++)
        {
            caracterRandom = random.nextInt(9) + 48;
            stringBuffer.append((char) caracterRandom);
        }

        return stringBuffer.toString();
    }

    private void alertaInfo(String random)
    {
        Alert ventana=new Alert(Alert.AlertType.INFORMATION);
        ventana.setTitle("¡Éxito al generar una nueva clave!");
        ventana.setHeaderText("Se ha generado una nueva clave para el paciente satisfactioramente.");
        ventana.setContentText(random);
        ventana.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        ventana.showAndWait();
    }

    private void alertaError()
    {
        Alert ventana=new Alert(Alert.AlertType.ERROR);
        ventana.setTitle("¡Error al generar nueva clave!");
        ventana.setHeaderText("Error: No se pudo generar una nueva clave para el paciente");
        ventana.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        ventana.showAndWait();
    }

    //Muestra un cuadro de dialogo, donde pide confirmación para generar una nueva clave para el paciente, retornando un booleano
    private boolean alertaConfirmar(Paciente dato)
    {
        Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);
        String contenido = "Paciente: "+dato.getNombre()+"\nRut: "+dato.getRut();

        ventana.setTitle("Confirmar Generación de Clave Nueva para Paciente");
        ventana.setHeaderText(contenido);
        ventana.initStyle(StageStyle.UTILITY);
        ventana.setContentText("¿Realmente desea generar una nueva clave para este paciente?");


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
