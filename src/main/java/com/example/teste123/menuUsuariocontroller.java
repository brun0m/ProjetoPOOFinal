package com.example.teste123;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ResourceBundle;

public class menuUsuariocontroller {
    @FXML
    private Button BotaoSairConta;
    @FXML
    private Label NomeMenu;
    @FXML
    private Label UsuarioMenu;
    @FXML
    private Label PlanoMenu;
    @FXML
    private Button BotaoEntrarAgendamento;
    @FXML
    private Button BotaoEntrarAlterar;
    @FXML
    private Button EntrarPesquisa;

    public menuUsuariocontroller(){}

    @FXML
    public void BotaoEntrarPesquisaOnAction(ActionEvent event){
        try {
            Usuario user = new Usuario(UsuarioMenu.getText());
            HelloApplication.Loginsusuario.add(user);
            Stage stage = (Stage) EntrarPesquisa.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("pesquisar-view.fxml"));
            Stage AgendarStage = new Stage();
            AgendarStage.initStyle(StageStyle.UNDECORATED);
            AgendarStage.setScene(new Scene(root, 640, 480));
            AgendarStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void BotaoEntrarAgendamentoOnAction(ActionEvent event) {
        try {
            Usuario user = new Usuario(UsuarioMenu.getText());
            HelloApplication.Loginsusuario.add(user);
            Stage stage = (Stage) BotaoEntrarAgendamento.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("agendarconsulta-view.fxml"));
            Stage AgendarStage = new Stage();
            AgendarStage.initStyle(StageStyle.UNDECORATED);
            AgendarStage.setTitle("Agendamento");
            AgendarStage.setScene(new Scene(root, 800, 600));
            AgendarStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    @FXML
    public void BotaoEntrarAlterarDadosOnAction(ActionEvent event){
        try {
            Usuario user = new Usuario(UsuarioMenu.getText());
            HelloApplication.Loginsusuario.add(user);
            Stage stage = (Stage) BotaoEntrarAlterar.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("alterardadoUsuario-view.fxml"));
            Stage AlterarStage = new Stage();
            AlterarStage.initStyle(StageStyle.UNDECORATED);
            AlterarStage.setScene(new Scene(root, 600, 400));
            AlterarStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void BotaoSairContaOnAction(ActionEvent event){
        Stage stage = (Stage) BotaoSairConta.getScene().getWindow();
        stage.close();
        HelloApplication.mudarTela(1);
    }

    @FXML
    public void initialize(){
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();

        String mostrarDados = "SELECT Nome, Usuario, Plano from new_table WHERE Usuario='" +
                HelloApplication.Loginsusuario.get(HelloApplication.Loginsusuario.size()-1).getUsuario() + "'";

        try{
            Statement statementDados = connectDB2.createStatement();
            ResultSet resultadoDados = statementDados.executeQuery(mostrarDados);
            while(resultadoDados.next()) {
                NomeMenu.setText(resultadoDados.getString(1));
                UsuarioMenu.setText(resultadoDados.getString(2));
                PlanoMenu.setText(resultadoDados.getString(3));
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}
