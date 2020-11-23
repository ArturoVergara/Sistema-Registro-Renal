package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;
import models.PersonalMedico;
import models.enums.PrevisionEnum;

import java.net.URL;
import java.util.ResourceBundle;

public class FormularioAgregarPacienteController implements Initializable
{
    @FXML
    private BorderPane parentContainer;
    @FXML
    private JFXTextField nombre;
    @FXML
    private JFXTextField rut;
    @FXML
    private JFXDatePicker fechaNacimiento;
    @FXML
    private JFXTextField direccion;
    @FXML
    private JFXComboBox<PrevisionEnum> prevision;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField telefono;

    private PersonalMedico usuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void inicializar(PersonalMedico usuario)
    {
        this.usuario = usuario;

        prevision.setItems(FXCollections.observableArrayList(PrevisionEnum.FONASA, PrevisionEnum.ISAPRE, PrevisionEnum.CAPREDENA));

        prevision.setConverter(new StringConverter<PrevisionEnum>() {
            @Override
            public String toString(PrevisionEnum objeto)
            {
                return objeto == null ? "" : objeto.getNombre();
            }

            @Override
            public PrevisionEnum fromString(String string)
            {
                return PrevisionEnum.valueOf(string);
            }
        });

    }
}
