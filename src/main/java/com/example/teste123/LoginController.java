package com.example.teste123;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.Scene;

import java.sql.Connection;

public class LoginController {

    @FXML
    private TextField NomeDoUsuario;
    @FXML
    private TextField Senha;
    @FXML
    private Label Aviso;

    @FXML
    public void BotaoLoginOnAction(ActionEvent event){
        if(NomeDoUsuario.getText().isBlank() == false && Senha.getText().isBlank() == false){
            validarLogin();
        }
        else {
            Aviso.setText("Entre com nome de Usuário e Senha");
        }
    }

    @FXML
    public void BotaoCancelarOnAction(ActionEvent event){HelloApplication.mudarTela(0);}

    public void validarLogin(){
        DataBaseConexao conectarAgora = new DataBaseConexao();
        Connection connectDB = conectarAgora.getConnection();
        String verificarLogin = "SELECT count(1) from new_table WHERE Usuario='" + NomeDoUsuario.getText() + "' and Senha = '" + Senha.getText() + "'";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verificarLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    HelloApplication.mudarTela(2);
                } else {
                    Aviso.setText("Login inválido! Tente novamente");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }




}
