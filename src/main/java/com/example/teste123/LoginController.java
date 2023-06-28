package com.example.teste123;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {

    //LOGIN CONTROLLER
    @FXML
    private TextField NomeDoUsuario;
    @FXML
    private TextField Senha;
    @FXML
    private Label Aviso;

    @FXML
    public void BotaoLoginOnAction(ActionEvent event) {
        if (NomeDoUsuario.getText().isBlank() == false && Senha.getText().isBlank() == false) {
            validarLogin();
        } else {
            Aviso.setText("Entre com nome de Usuário e Senha");
        }
    }

    @FXML
    public void BotaoCancelarOnAction(ActionEvent event) {
        HelloApplication.mudarTela(0);
    }

    public void validarLogin() {
        DataBaseConexao conectarAgora = new DataBaseConexao();
        Connection connectDB = conectarAgora.getConnection();
        String verificarLogin = "SELECT count(1) from new_table WHERE Usuario='" + NomeDoUsuario.getText() + "' and Senha = '" + Senha.getText() + "'";
        String verificarLogin2 = "SELECT count(1) from new_medico WHERE Usuario='" + NomeDoUsuario.getText() + "' and Senha = '" + Senha.getText() + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verificarLogin);
            Statement statement2 = connectDB.createStatement();
            ResultSet queryResult2 = statement2.executeQuery(verificarLogin2);

            while (queryResult.next() && queryResult2.next()) {
                if (queryResult.getInt(1) == 1) {
                    Usuario user = new Usuario(NomeDoUsuario.getText());
                    HelloApplication.Loginsusuario.add(user);
                    entrarMenuUsuario();
                } else if (queryResult2.getInt(1) == 1) {
                    Usuario user = new Usuario(NomeDoUsuario.getText());
                    HelloApplication.Loginsusuario.add(user);
                    entrarMenuMedico();
                } else {
                    Aviso.setText("Login inválido! Tente novamente");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void entrarMenuUsuario() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("menu-view.fxml"));
            Stage MenuStage = new Stage();
            MenuStage.initStyle(StageStyle.UNDECORATED);
            MenuStage.setTitle("Menu");
            MenuStage.setScene(new Scene(root, 640, 360));
            MenuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    public void entrarMenuMedico(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("menumedico-view.fxml"));
            Stage MenuStage = new Stage();
            MenuStage.initStyle(StageStyle.UNDECORATED);
            MenuStage.setTitle("Menu");
            MenuStage.setScene(new Scene(root, 640, 360));
            MenuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
