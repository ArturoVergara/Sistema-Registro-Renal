package controllers;

import DAO.ExamenDAOImpl;
import DAO.FichaMedicaDAO;
import DAO.FichaMedicaDAOImpl;
import DAO.PacienteDAOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import javafx.util.Pair;
import javafx.util.StringConverter;
import models.Examen;
import models.Paciente;
import models.PersonalMedico;
import models.enums.ExamenEnum;
import models.enums.PersonalEnum;
import models.enums.PrevisionEnum;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TablaExamenesController implements Initializable
{
    @FXML
    private BorderPane parentContainer;
    @FXML
    private Label nombreUsuario;
    @FXML
    private Label nombrePaciente;
    @FXML
    private Label rutPaciente;
    @FXML
    private TableView<Examen> tabla;
    @FXML
    private TableColumn<Examen, String> columnaTipo;
    @FXML
    private TableColumn<Examen, Float> columnaResultado;
    @FXML
    private TableColumn<Examen, LocalDateTime> columnaFecha;
    @FXML
    private TableColumn<Examen, String> columnaAcciones;

    private PersonalMedico usuario;
    private Paciente paciente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void inicializar(PersonalMedico usuario, Paciente paciente)
    {
        this.usuario = usuario;
        this.paciente = paciente;
        nombreUsuario.setText(usuario.getNombre());

        nombrePaciente.setText(paciente.getNombre());
        rutPaciente.setText(paciente.getRut());

        //PacienteDAOImpl pacienteDAO = new PacienteDAOImpl();
        //List<Paciente> pacientes = pacienteDAO.getPacientes();
        FichaMedicaDAOImpl fichaMedicaDAO = new FichaMedicaDAOImpl();
        List<Examen> examenes = fichaMedicaDAO.getExamenesPaciente(paciente.getFichaPaciente());

        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("tipoExamen"));
        columnaResultado.setCellValueFactory(new PropertyValueFactory<>("resultadoExamen"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fechaEmision"));
        columnaAcciones.setCellValueFactory(new PropertyValueFactory<>("ASDASD"));


        //Callback para reemplazar el valor de la columna Acciones por los botones eliminar
        Callback<TableColumn<Examen, String>, TableCell<Examen, String>> cellFactory =  new Callback<TableColumn<Examen, String>, TableCell<Examen, String>>()
        {
            @Override
            public TableCell call(TableColumn<Examen, String> param)
            {
                TableCell<Examen, String> cell = new TableCell<Examen, String>()
                {
                    @Override
                    public void updateItem(String item, boolean empty)
                    {
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
                            botonEliminar.setOnAction(event -> {
                                Examen dato = getTableView().getItems().get(getIndex());
                                eliminarExamen(dato, getIndex());
                            });

                            hbox.setSpacing(10.0);

                            //Setear estilos a los botones
                            botonEliminar.setStyle("-fx-background-color: #ff1744; -fx-text-fill: white");
                            botonEliminar.setCursor(Cursor.HAND);

                            hbox.getChildren().addAll(botonEliminar);
                            setGraphic(hbox);
                            setText(null);
                        }
                    }
                };

                return cell;
            }
        };

        columnaAcciones.setCellFactory(cellFactory);

        if (examenes != null)
            tabla.setItems(FXCollections.observableArrayList(examenes));
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
    void cargarVistaPerfilPaciente()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/PerfilPaciente.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);

            //Obtiene el controlador de TablaPacientes
            PerfilPacienteController controlador = (PerfilPacienteController) loader.getController();
            controlador.inicializar(usuario, paciente);

            Stage ventana = (Stage) parentContainer.getScene().getWindow();
            ventana.setScene(escena);
            ventana.show();
        }
        catch (IOException | IllegalStateException excepcion)
        {
            alertaExcepcion(excepcion);
        }
    }

    void eliminarExamen(Examen dato, int indice)
    {
        if (alertaEliminar())
        {
            ExamenDAOImpl examenDAO = new ExamenDAOImpl();

            if (examenDAO.deleteExamenPaciente(dato.getId()))
            {
                tabla.getItems().remove(indice);
                alertaInfo();
            }
            else
                alertaError();
        }
    }

    @FXML
    private void agregarExamen()
    {
        Dialog<Pair<ExamenEnum,Float>> ventana = new Dialog<>();

        ventana.setTitle("Agregar Exámen");
        ventana.setHeaderText("Por favor ingrese los siguientes campos para agregar un exámen.");

        ButtonType botonCrear = new ButtonType("Crear", ButtonBar.ButtonData.OK_DONE);
        ventana.getDialogPane().getButtonTypes().addAll(botonCrear, ButtonType.CANCEL);

        GridPane grid = new GridPane();

        grid.setHgap(15);
        grid.setVgap(15);

        ComboBox<ExamenEnum> tipoExamen = new ComboBox<>();
        tipoExamen.setItems(FXCollections.observableArrayList(ExamenEnum.ALBUMINA, ExamenEnum.CREATININA, ExamenEnum.UREA));

        tipoExamen.setConverter(new StringConverter<ExamenEnum>() {
            @Override
            public String toString(ExamenEnum objeto)
            {
                return objeto == null ? "" : objeto.getNombre();
            }

            @Override
            public ExamenEnum fromString(String string)
            {
                return ExamenEnum.valueOf(string);
            }
        });

        TextField resultado = new TextField();

        resultado.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if (!newValue.matches("(\\d{0,3},)?\\d{0,3}"))
                    resultado.setText(oldValue);
            }
        });

        grid.add(new Label("Tipo: "), 0, 0);
        grid.add(tipoExamen,1,0);
        grid.add(new Label("Resultado:"),0,1);
        grid.add(resultado,1,1);

        ventana.getDialogPane().setContent(grid);

        ventana.setResultConverter(botonDialogo -> {
            if (botonDialogo == botonCrear) {
                return new Pair<ExamenEnum, Float>(tipoExamen.getValue(), Float.parseFloat(resultado.getText().replace(',', '.')));
            }

            return  null;
        });

        ExamenDAOImpl examenDAO = new ExamenDAOImpl();

        Optional<Pair<ExamenEnum,Float>> resultadoVentana = ventana.showAndWait();

        resultadoVentana.ifPresent(datos -> {
            ExamenEnum tipoExamenFinal = datos.getKey();
            Float resultadoFinal = datos.getValue();

            Examen examen = new Examen(tipoExamenFinal, resultadoFinal);

            if (examenDAO.createExamenPaciente(paciente, examen) != null)
            {
                alertaInfoAgregar();
                FichaMedicaDAOImpl fichaMedicaDAO = new FichaMedicaDAOImpl();
                List<Examen> examenes = fichaMedicaDAO.getExamenesPaciente(paciente.getFichaPaciente());
                if (examenes != null)
                    tabla.setItems(FXCollections.observableArrayList(examenes));
            }
            else
                alertaErrorAgregar();
        });
    }

    private void alertaErrorAgregar()
    {
        Alert ventana=new Alert(Alert.AlertType.ERROR);
        ventana.setTitle("¡Error al agregar!");
        ventana.setHeaderText("Error: No se pudo agregar el examen a la base de datos");
        ventana.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        ventana.showAndWait();
    }

    private void alertaError()
    {
        Alert ventana=new Alert(Alert.AlertType.ERROR);
        ventana.setTitle("¡Error al eliminar!");
        ventana.setHeaderText("Error: No se pudo eliminar el examen de la base de datos");
        ventana.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        ventana.showAndWait();
    }

    //Muestra un cuadro de dialogo, donde pide confirmación para eliminar el examen, retornando un booleano
    private boolean alertaEliminar()
    {
        Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);

        ventana.setTitle("Confirmar Eliminación de Examen");
        ventana.initStyle(StageStyle.UTILITY);
        ventana.setContentText("¿Realmente desea eliminar este examen?");

        Optional<ButtonType> opcion=ventana.showAndWait();

        if (opcion.get() == ButtonType.OK)
            return true;

        return false;
    }

    private void alertaInfoAgregar()
    {
        Alert ventana=new Alert(Alert.AlertType.INFORMATION);
        ventana.setTitle("¡Éxito al agregar!");
        ventana.setHeaderText("Se ha creado el examen satisfactioramente.");
        ventana.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        ventana.showAndWait();
    }

    private void alertaInfo()
    {
        Alert ventana=new Alert(Alert.AlertType.INFORMATION);
        ventana.setTitle("¡Éxito al eliminar!");
        ventana.setHeaderText("Se ha eliminado al paciente satisfactioramente.");
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
