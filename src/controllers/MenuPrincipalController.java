package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuPrincipalController implements Initializable
{
    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    @FXML
    private void finalizarPrograma(ActionEvent event){}

    @FXML
    private void cargarVentanaAnterior(ActionEvent event){}

    @FXML
    private void cargarAdministrarAlimentos(ActionEvent event){}

    @FXML
    private void cargarAdministrarBebestibles(ActionEvent event){}

    @FXML
    private void cargarAdministrarIngredientes(ActionEvent event){}

    @FXML
    private void cargarAdministrarMesas(ActionEvent event){}

    @FXML
    private void cargarReportes(ActionEvent event){}

    @FXML
    private void guardarCambios(ActionEvent event){}
}
