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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
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
        mensagemRegistrar.setText("");
        HelloApplication.mudarTela(0);
    }

    @FXML
    public void BotaoCadastrarMedicoOnAction(ActionEvent event){
        if (nome.getText().isBlank() == true || usuario.getText().isBlank() == true){
            mensagemRegistrar.setText("Digite seu nome e usuário!");
        } else if(EspecialidadeEscolhida.getText().isBlank() == true){
            mensagemRegistrar.setText("Escolha sua especialidade!");
        }
        else if(Senha1.getText().isBlank() == true){
            AvisoSenha.setText("Digite uma senha!");
        }
        else if(Senha1.getText().equals(Senha2.getText()) == false){
            AvisoSenha.setText("Senhas não estão iguais!");
        }
        else{
            registrarMedico();
            AvisoSenha.setText("");
            Senha1.setText("");
            Senha2.setText("");
            nome.setText("");
            usuario.setText("");
            EspecialidadeEscolhida.setText("");
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
                "CAMED, AMIL, UNIMED, SEMPLANO, Especialidade, UltimasAvaliacao) VALUES (";
        String insertValues = "'" + Nome + "','"  + Usuario + "','" + Senha + "','" + 0 + "','" + 0 + "','" + bradesco +
                "','" + camed + "','" + amil + "','" + unimed + "','true','" + esp + "','')";
        String InsertToRegister = insertFields + insertValues;

        try{
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(InsertToRegister);
            mensagemRegistrar.setText("Médico registrado com sucesso!");
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
    @FXML
    private ImageView FotoCadeado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ObservableList<String> ListaDeEspecialidades = FXCollections.observableArrayList("CLINICA GERAL",
                "GINECOLOGIA", "ORTOPEDIA", "DERMATOLOGIA","UROLOGIA", "OFTALMOLOGIA", "NEUROLOGIA", "CARDIOLOGIA");
        CBEspecialidades.setItems(ListaDeEspecialidades);
        File medicoFile = new File("C:/Users/silve/OneDrive/Pictures/cadeado.jpg");
        Image medicoImagem = new Image(medicoFile.toURI().toString());
        FotoCadeado.setImage(medicoImagem);

    }

}
