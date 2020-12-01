package controllers;

import DAO.DiagnosticoDAOImpl;
import DAO.ExamenDAOImpl;
import DAO.FichaMedicaDAOImpl;
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
import models.Diagnostico;
import models.Paciente;
import models.PersonalMedico;
import models.enums.CategoriaDanioEnum;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TablaDiagnosticosController implements Initializable
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
    private TableView<Diagnostico> tabla;
    @FXML
    private TableColumn<Diagnostico, CategoriaDanioEnum> columnaDano;
    @FXML
    private TableColumn<Diagnostico, Float> columnaResultado;
    @FXML
    private TableColumn<Diagnostico, String> columnaDescripcion;
    @FXML
    private TableColumn<Diagnostico, LocalDateTime> columnaFecha;
    @FXML
    private TableColumn<Diagnostico, String> columnaAccion;

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

        FichaMedicaDAOImpl fichaMedicaDAO = new FichaMedicaDAOImpl();
        List<Diagnostico> diagnosticos = fichaMedicaDAO.getDiagnosticosPaciente(paciente.getFichaPaciente());

        columnaDano.setCellValueFactory(new PropertyValueFactory<>("categoriaDanioPaciente"));
        columnaResultado.setCellValueFactory(new PropertyValueFactory<>("resultadoFiltradoGlomerular"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcionDiagnostico"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fechaActualizacion"));
        columnaAccion.setCellValueFactory(new PropertyValueFactory<>("ASDASD"));


        //Callback para reemplazar el valor de la columna Acciones por los botones eliminar
        Callback<TableColumn<Diagnostico, String>, TableCell<Diagnostico, String>> cellFactory =  new Callback<TableColumn<Diagnostico, String>, TableCell<Diagnostico, String>>()
        {
            @Override
            public TableCell call(TableColumn<Diagnostico, String> param)
            {
                TableCell<Diagnostico, String> cell = new TableCell<Diagnostico, String>()
                {
                    @Override
                    public void updateItem(String item, boolean empty)
                    {
                        JFXButton botonEliminar = new JFXButton("Eliminar");

                        super.updateItem(item, empty);

                        if (empty)
                        {
                            setGraphic(null);
                            setText(null);
                        }
                        else
                        {
                            botonEliminar.setOnAction(event -> {
                                Diagnostico dato = getTableView().getItems().get(getIndex());
                                eliminarDiagnostico(dato, getIndex());
                            });

                            //Setear estilos a los botones
                            botonEliminar.setStyle("-fx-background-color: #ff1744; -fx-text-fill: white");
                            botonEliminar.setCursor(Cursor.HAND);

                            setGraphic(botonEliminar);
                            setText(null);
                        }
                    }
                };

                return cell;
            }
        };

        columnaAccion.setCellFactory(cellFactory);

        if (diagnosticos != null)
            tabla.setItems(FXCollections.observableArrayList(diagnosticos));
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

    void eliminarDiagnostico(Diagnostico dato, int indice)
    {
        if (alertaEliminar())
        {
            DiagnosticoDAOImpl diagnosticoDAO = new DiagnosticoDAOImpl();

            if (diagnosticoDAO.deleteDiagnosticoPaciente(dato.getId()))
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
        ventana.setHeaderText("Error: No se pudo eliminar el diagnóstico de la base de datos");
        ventana.initStyle(StageStyle.UTILITY);
        java.awt.Toolkit.getDefaultToolkit().beep();
        ventana.showAndWait();
    }

    //Muestra un cuadro de dialogo, donde pide confirmación para eliminar el diagnostico, retornando un booleano
    private boolean alertaEliminar()
    {
        Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);

        ventana.setTitle("Confirmar Eliminación de Diagnóstico");
        ventana.initStyle(StageStyle.UTILITY);
        ventana.setContentText("¿Realmente desea eliminar este diagnóstico?");

        Optional<ButtonType> opcion=ventana.showAndWait();

        if (opcion.get() == ButtonType.OK)
            return true;

        return false;
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
