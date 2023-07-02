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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.w3c.dom.Text;

import java.io.File;
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
        mensagemRegistrar.setText("");
        usuario.setText("");
        nome.setText("");
        PlanoEscolhido.setText("");
        AvisoSenha.setText("");
        Senha1.setText("");
        Senha2.setText("");
        HelloApplication.mudarTela(0);
    }

    @FXML
    public void BotaoCadastrarUsuarioOnAction(ActionEvent event){
        if (nome.getText().isBlank() == true || usuario.getText().isBlank() == true){
            mensagemRegistrar.setText("Digite seu nome e usuário!");
        } else if(PlanoEscolhido.getText().isBlank() == true){
            mensagemRegistrar.setText("Escolha seu plano!");
        } else if(Senha1.getText().isBlank() == true){
            AvisoSenha.setText("Digite uma senha!");
        } else if(Senha1.getText().equals(Senha2.getText()) == false){
            AvisoSenha.setText("Senhas não estão iguais!");
            registrarUsuario();
            AvisoSenha.setText("");
        } else{
            registrarUsuario();
            AvisoSenha.setText("");
            AvisoSenha.setText("");
            Senha1.setText("");
            Senha2.setText("");
            nome.setText("");
            usuario.setText("");
            PlanoEscolhido.setText("");
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

    @FXML
    private ImageView FotoCadeado;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> ListaDePlanos = FXCollections.observableArrayList("CAMED", "BRADESCO", "UNIMED",
                "AMIL","SEMPLANO");
        CBPlanos.setItems(ListaDePlanos);
        File medicoFile = new File("C:/Users/silve/OneDrive/Pictures/cadeado.jpg");
        Image medicoImagem = new Image(medicoFile.toURI().toString());
        FotoCadeado.setImage(medicoImagem);
    }
}
