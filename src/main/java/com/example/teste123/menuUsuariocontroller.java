package com.example.teste123;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    public menuUsuariocontroller(){
    }

    @FXML
    public void BotaoSairContaOnAction(ActionEvent event){
        Stage stage = (Stage) BotaoSairConta.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void BotaoEntrarAgendamentoOnAction(ActionEvent event) { HelloApplication.mudarTela(4);}

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
