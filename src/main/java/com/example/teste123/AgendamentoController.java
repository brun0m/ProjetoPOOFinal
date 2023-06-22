package com.example.teste123;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;
public class AgendamentoController implements Initializable{

    @FXML
    public void BotaoEntrarMenuOnAction(ActionEvent event) { HelloApplication.mudarTela((2)); }

    @FXML
    public void BotaoEntrarAgendamentoOnAction(ActionEvent event) { HelloApplication.mudarTela((4)); }

    @FXML
    public void BotaoEntrarClinicaGeralOnAction(ActionEvent event) { HelloApplication.mudarTela((5)); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
