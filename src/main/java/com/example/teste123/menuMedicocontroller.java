package com.example.teste123;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class menuMedicocontroller {
    @FXML
    private Button BotaoSairConta, BotaoHistorico;
    @FXML
    private Button BotaoAlteracao;
    @FXML
    private Label NomeMenu;
    @FXML
    private Label UsuarioMenu;
    @FXML
    private Label PlanosMenu;
    @FXML
    private Label EstrelasMenu;
    @FXML
    private Label EspecialidadeMenu;
    @FXML
    private Label AtendimentosMenu;
    @FXML
    private Button ButtonEntrarConsultas;

    public menuMedicocontroller(){}

    @FXML
    public void BotaoSairContaOnAction(ActionEvent event){
        Stage stage = (Stage) BotaoSairConta.getScene().getWindow();
        stage.close();
        HelloApplication.mudarTela(1);
    }

    @FXML
    public void BotaoEntrarHistoricosOnAction(ActionEvent event) {
        try {
            Usuario user = new Usuario(UsuarioMenu.getText());
            HelloApplication.Loginsusuario.add(user);
            Stage stage = (Stage) BotaoHistorico.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("historicomedico=view.fxml"));
            Stage AlterarStage = new Stage();
            AlterarStage.initStyle(StageStyle.UNDECORATED);
            AlterarStage.setScene(new Scene(root, 800, 400));
            AlterarStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void BotaoEntrarConsultasOnAction(ActionEvent event) {
        try {
            Usuario user = new Usuario(UsuarioMenu.getText());
            HelloApplication.Loginsusuario.add(user);
            Stage stage = (Stage) ButtonEntrarConsultas.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("verConsultaMedico-view.fxml"));
            Stage AlterarStage = new Stage();
            AlterarStage.initStyle(StageStyle.UNDECORATED);
            AlterarStage.setScene(new Scene(root, 800, 600));
            AlterarStage.show();
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
            Stage stage = (Stage) BotaoAlteracao.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("alterardadoMed-view.fxml"));
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
    public void initialize(){
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();

        String mostrarDados = "SELECT Nome, Usuario, Avaliacao, AMIL, BRADESCO, CAMED, UNIMED," +
                " NumDeAvaliacao, Especialidade from new_medico WHERE Usuario='" +
                HelloApplication.Loginsusuario.get(HelloApplication.Loginsusuario.size()-1).getUsuario() + "'";

        try{
            Statement statementDados = connectDB2.createStatement();
            ResultSet resultadoDados = statementDados.executeQuery(mostrarDados);
            while(resultadoDados.next()) {
                NomeMenu.setText(resultadoDados.getString("Nome"));
                UsuarioMenu.setText(resultadoDados.getString("Usuario"));
                EstrelasMenu.setText(resultadoDados.getString("Avaliacao"));
                AtendimentosMenu.setText(resultadoDados.getString("NumDeAvaliacao"));
                EspecialidadeMenu.setText(resultadoDados.getString("Especialidade"));
                if(resultadoDados.getString("AMIL").equals("true")){
                    PlanosMenu.setText(PlanosMenu.getText() + "AMIL, ");
                }if(resultadoDados.getString("BRADESCO").equals("true")){
                    PlanosMenu.setText(PlanosMenu.getText() + "BRADESCO, ");
                }if(resultadoDados.getString("CAMED").equals("true")){
                    PlanosMenu.setText(PlanosMenu.getText() + "CAMED, ");
                }if(resultadoDados.getString("UNIMED").equals("true")){
                    PlanosMenu.setText(PlanosMenu.getText() + "UNIMED");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
