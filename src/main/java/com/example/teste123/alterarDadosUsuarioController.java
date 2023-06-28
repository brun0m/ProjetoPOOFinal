package com.example.teste123;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class alterarDadosUsuarioController{
    @FXML
    private ComboBox CBPlanos;
    @FXML
    private Label confirmarRegistro;
    @FXML
    private PasswordField Senha1;
    @FXML
    private PasswordField Senha2;
    @FXML
    private Label AvisoSenha;
    @FXML
    private Label PlanoEscolhido;
    @FXML
    private TextField nome;
    @FXML
    private Label usuario;
    @FXML
    private Button SairAlteracao;

    @FXML
    public void BotaoSairAlteracaoOnAction(ActionEvent event){
        try {
            Usuario user = new Usuario(usuario.getText());
            HelloApplication.Loginsusuario.add(user);
            Stage stage = (Stage) SairAlteracao.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("menu-view.fxml"));
            Stage AgendarStage = new Stage();
            AgendarStage.initStyle(StageStyle.UNDECORATED);
            AgendarStage.setScene(new Scene(root, 640, 360));
            AgendarStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void BotaoAlterarOnAction(ActionEvent event){
        if(Senha1.getText().equals(Senha2.getText())){
            alterarUsuario();
            AvisoSenha.setText("");
        }
        else{
            AvisoSenha.setText("Senhas não estão iguais!");
        }
    }

    public void alterarUsuario(){
        DataBaseConexao conectarAgora = new DataBaseConexao();
        Connection connectionDB = conectarAgora.getConnection();

        String Nome = nome.getText();
        String Usuario = usuario.getText();
        String Senha = Senha1.getText();
        String Plano = PlanoEscolhido.getText();

        String alterarRegistros = "UPDATE new_table SET Nome='" + Nome + "', Usuario='" + Usuario + "', Senha='" + Senha +
                "', Plano='" + Plano + "' WHERE Usuario='" + usuario.getText() + "'";

        try{
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(alterarRegistros);
            confirmarRegistro.setText("Alteração feita com sucesso!");
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void initialize(){
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();

        String mostrarDados = "SELECT Nome, Usuario, Plano, Senha from new_table WHERE Usuario='" +
                HelloApplication.Loginsusuario.get(HelloApplication.Loginsusuario.size()-1).getUsuario() + "'";

        try{
            Statement statementDados = connectDB2.createStatement();
            ResultSet resultadoDados = statementDados.executeQuery(mostrarDados);
            while(resultadoDados.next()) {
                nome.setText(resultadoDados.getString(1));
                usuario.setText(resultadoDados.getString(2));
                PlanoEscolhido.setText(resultadoDados.getString(3));
                Senha1.setText(resultadoDados.getString(4));
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
        ObservableList<String> ListaDePlanos = FXCollections.observableArrayList("CAMED", "BRADESCO", "UNIMED",
                "AMIL","SEM PLANO");
        CBPlanos.setItems(ListaDePlanos);
    }

    @FXML
    public void SelecionarPlano(ActionEvent event){
        String plano = CBPlanos.getSelectionModel().getSelectedItem().toString();
        PlanoEscolhido.setText(plano);
    }

}
