package com.example.teste123;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.net.URL;
public class cadastroMedicoController implements Initializable {

    @FXML
    private ComboBox CBEspecialidades;
    @FXML
    private Label mensagemRegistrar;
    @FXML
    private PasswordField Senha1;
    @FXML
    private PasswordField Senha2;
    @FXML
    private Label AvisoSenha;
    @FXML
    private Label EspecialidadeEscolhida;
    @FXML
    private TextField nome;
    @FXML
    private TextField usuario;
    @FXML
    private RadioButton CAMED;
    @FXML
    private RadioButton BRADESCO;
    @FXML
    private RadioButton UNIMED;
    @FXML
    private RadioButton AMIL;

    @FXML
    public void BotaoEntrarInicioOnAction(ActionEvent event){
        HelloApplication.mudarTela(0);
    }

    @FXML
    public void BotaoCadastrarMedicoOnAction(ActionEvent event){
        if(Senha1.getText().equals(Senha2.getText())){
            registrarMedico();
            AvisoSenha.setText("");
        }
        else{
            AvisoSenha.setText("Senhas não estão iguais!");
        }
    }

    public void registrarMedico(){
        DataBaseConexao conectarAgora = new DataBaseConexao();
        Connection connectionDB = conectarAgora.getConnection();

        String Nome = nome.getText();
        String Usuario = usuario.getText();
        String Senha = Senha1.getText();
        String bradesco  = String.valueOf(BRADESCO.isSelected());
        String camed  = String.valueOf(CAMED.isSelected());
        String amil  = String.valueOf(AMIL.isSelected());
        String unimed = String.valueOf(UNIMED.isSelected());
        String esp = EspecialidadeEscolhida.getText();

        String insertFields = "INSERT INTO new_medico(Nome, Usuario, Senha, Avaliacao, NumDeAvaliacao, BRADESCO, " +
                "CAMED, AMIL, UNIMED, Especialidade, UltimasAvaliacao) VALUES (";
        String insertValues = "'" + Nome + "','"  + Usuario + "','" + Senha + "','" + 0 + "','" + 0 + "','" + bradesco +
                "','" + camed + "','" + amil + "','" + unimed + "','" + esp + "','')";
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
    public void SelecionarEspecialidade(ActionEvent event){
        String esp = CBEspecialidades.getSelectionModel().getSelectedItem().toString();
        EspecialidadeEscolhida.setText(esp);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ObservableList<String> ListaDeEspecialidades = FXCollections.observableArrayList("CLINICA GERAL",
                "GINECOLOGIA", "ORTOPEDIA", "DERMATOLOGIA","UROLOGIA", "OFTALMOLOGIA", "NEUROLOGIA", "CARDIOLOGIA");
        CBEspecialidades.setItems(ListaDeEspecialidades);

    }

}
