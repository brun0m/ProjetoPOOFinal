package com.example.teste123;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.*;

import java.util.ResourceBundle;
import java.net.URL;

public class HelloController implements Initializable {

    //CÓDIGO PARA APLICAR A AÇÃO DOS BOTOES
    @FXML
    private Button botaozincentral;

    @FXML
    private Button CancelBotao;


    public void botaozincentralOnAction(ActionEvent event) {
        Stage stage = (Stage) botaozincentral.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void BotaoEntrarLoginOnAction(ActionEvent event){
        HelloApplication.mudarTela(1);
    }

    @FXML
    public void BotaoEntrarMenuOnAction(ActionEvent event) { HelloApplication.mudarTela((2)); }

    @FXML
    public void BotaoEntrarCadUsuarioOnAction(ActionEvent event){HelloApplication.mudarTela(3);}

    @FXML
    public void BotaoEntrarAgendamentoOnAction(ActionEvent event) { HelloApplication.mudarTela(4);}

    @FXML
    public void BotaoConfirmarCadastroMedicoOnAction(ActionEvent event) { HelloApplication.mudarTela(1);}

    @FXML
    public void BotaoEntrarCadastroMedicoOnAction(ActionEvent event) { HelloApplication.mudarTela(6);}


    //CÓDIGO PARA MOSTRAR AS IMAGENS

    @FXML
    private ImageView FotoComputacao;
    @FXML
    private ImageView FotoUECE;

    @Override
    public void initialize(URL url, ResourceBundle rB){
    //    File medicoFile = new File("OneDrive/Pictures/uece.png");
    //    Image medicoImagem = new Image(medicoFile.toURI().toString());
    //    FotoUECE.setImage(medicoImagem);
    //    File usuarioFile = new File("OneDrive/Pictures/Computacao.jpg");
    //    Image usuarioImagem = new Image(usuarioFile.toURI().toString());
    //    FotoComputacao.setImage(usuarioImagem);
    }




}