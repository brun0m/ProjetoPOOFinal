package com.example.teste123;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
public class AgendamentoController{
    @FXML
    private Button VoltarMenu;
    @FXML
    private Label UsuarioAgendamento;
    @FXML
    public void BotaoEntrarMenuOnAction(ActionEvent event) {
        try {
            Usuario user = new Usuario(UsuarioAgendamento.getText());
            HelloApplication.Loginsusuario.add(user);
            Stage stage = (Stage) VoltarMenu.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("menu-view.fxml"));
            Stage MenuStage = new Stage();
            MenuStage.initStyle(StageStyle.UNDECORATED);
            MenuStage.setScene(new Scene(root, 640, 360));
            MenuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void BotaoEntrarClinicaGeralOnAction(ActionEvent event) { HelloApplication.mudarTela((5)); }

    @FXML
    public void initialize() {
        DataBaseConexao conectarAgenda = new DataBaseConexao();
        Connection connectDB3 = conectarAgenda.getConnection();

        String mostrarUsuario = "SELECT Usuario from new_table WHERE Usuario='" +
                HelloApplication.Loginsusuario.get(HelloApplication.Loginsusuario.size()-1).getUsuario() + "'";

        try{
            Statement statementDados = connectDB3.createStatement();
            ResultSet resultadoDados = statementDados.executeQuery(mostrarUsuario);
            while(resultadoDados.next()) {
                UsuarioAgendamento.setText(resultadoDados.getString(1));
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
