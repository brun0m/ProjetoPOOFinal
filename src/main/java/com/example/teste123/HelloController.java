package com.example.teste123;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;

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

    public void cancelBotaoOnAction(ActionEvent event){
        Stage stage = (Stage) CancelBotao.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void loginBotaoOnAction(ActionEvent event){
        HelloApplication.mudarTela(2);

    }

    @FXML
    public void SairBotaomenuOnAction(ActionEvent event){
        HelloApplication.mudarTela(1);
    }

    @FXML
    public void EntrarLoginUsuarioOnAction(ActionEvent event){
        HelloApplication.mudarTela(1);
    }

    @FXML
    public void BotaoCadastrarUsuarioOnAction(ActionEvent event){
        HelloApplication.mudarTela(3);
    }

    @FXML
    public void BotaoConfirmarCadastroUsuarioOnAction(ActionEvent event){
        HelloApplication.mudarTela(1);
    }

    @FXML
    public void BotaoCancelarCadastroUsuarioOnAction(ActionEvent event){
        HelloApplication.mudarTela(0);
    }

    //CÓDIGO PARA MOSTRAR AS IMAGENS

    //@FXML
    //private ImageView FotoMedico;
    //@FXML
    //private ImageView FotoUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rB){
       // File medicoFile = new File("Imagens/fotomedico.jpg");
       // Image medicoImagem = new Image(medicoFile.toURI().toString());
       // FotoMedico.setImage(medicoImagem);
        //File usuarioFile = new File("Imagens/FotoUsuario.jpg");
       // Image usuarioImagem = new Image(usuarioFile.toURI().toString());
       // FotoUsuario.setImage(usuarioImagem);
    }




}