package com.example.teste123;

import javafx.event.ActionEvent;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ComboBox;
import org.w3c.dom.Text;

import java.net.URL;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ResourceBundle;

public class cadastroUsuarioController implements Initializable {
    @FXML
    private ComboBox CBPlanos;
    @FXML
    private Label mensagemRegistrar;
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
    private TextField usuario;


    @FXML
    public void BotaoEntrarInicioOnAction(ActionEvent event){
        HelloApplication.mudarTela(0);
    }

    @FXML
    public void BotaoCadastrarUsuarioOnAction(ActionEvent event){
        if(Senha1.getText().equals(Senha2.getText())){
            registrarUsuario();
            AvisoSenha.setText("");
        }
        else{
            AvisoSenha.setText("Senhas não estão iguais!");
        }

    }

    public void registrarUsuario(){
        DataBaseConexao conectarAgora = new DataBaseConexao();
        Connection connectionDB = conectarAgora.getConnection();

        String Nome = nome.getText();
        String Usuario = usuario.getText();
        String Senha = Senha1.getText();
        String Plano = PlanoEscolhido.getText();

        String insertFields = "INSERT INTO new_table(Nome, Usuario, Senha, Plano) VALUES (";
        String insertValues = "'" + Nome + "','"  + Usuario + "','" + Senha + "','" + Plano + "')";
        String InsertToRegister = insertFields + insertValues;

        try{
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(InsertToRegister);
            mensagemRegistrar.setText("Usuário registrado com sucesso!");
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }
    @FXML
    public void SelecionarPlano(ActionEvent event){
        String plano = CBPlanos.getSelectionModel().getSelectedItem().toString();
        PlanoEscolhido.setText(plano);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> ListaDePlanos = FXCollections.observableArrayList("CAMED", "BRADESCO", "UNIMED",
                "AMIL","SEM PLANO");
        CBPlanos.setItems(ListaDePlanos);
    }
}
