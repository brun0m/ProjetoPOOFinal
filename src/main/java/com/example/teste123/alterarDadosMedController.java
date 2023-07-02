package com.example.teste123;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class alterarDadosMedController {
    @FXML
    private Button SairAlterar;
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
    private Label usuario;
    @FXML
    private RadioButton CAMED;
    @FXML
    private RadioButton BRADESCO;
    @FXML
    private RadioButton UNIMED;
    @FXML
    private RadioButton AMIL;

    @FXML
    public void BotaoSairAlteracaoOnAction(ActionEvent event){
        try {
            Usuario user = new Usuario(usuario.getText());
            HelloApplication.Loginsusuario.add(user);
            Stage stage = (Stage) SairAlterar.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("menumedico-view.fxml"));
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
    public void BotaoAlterarMedicoOnAction(ActionEvent event){
        if (nome.getText().isBlank() == true){
            mensagemRegistrar.setText("Digite seu nome");
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
            alterarMedico();
            AvisoSenha.setText("");
        }
    }
    public void alterarMedico(){
        DataBaseConexao conectarAgora = new DataBaseConexao();
        Connection connectionDB = conectarAgora.getConnection();

        String Nome = nome.getText();
        String Senha = Senha1.getText();
        String Especialidade = EspecialidadeEscolhida.getText();
        String bradesco  = String.valueOf(BRADESCO.isSelected());
        String camed  = String.valueOf(CAMED.isSelected());
        String amil  = String.valueOf(AMIL.isSelected());
        String unimed = String.valueOf(UNIMED.isSelected());

        String alterarRegistros = "UPDATE new_medico SET Nome='" + Nome + "', Senha='" + Senha +
                "', Especialidade='" + Especialidade + "', CAMED='" + camed + "', BRADESCO='" + bradesco +
                "', AMIL='" + amil + "', UNIMED='" + unimed + "' WHERE Usuario='" + usuario.getText() + "'";

        try{
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(alterarRegistros);
            mensagemRegistrar.setText("Alteração feita com sucesso!");
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    private ImageView Cadeado;
    @FXML
    public void initialize() {
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();
        File medicoFile = new File("C:/Users/silve/OneDrive/Pictures/cadeado.jpg");
        Image medicoImagem = new Image(medicoFile.toURI().toString());
        Cadeado.setImage(medicoImagem);

        String mostrarDados = "SELECT Nome, Usuario, Especialidade, Senha, CAMED, BRADESCO, UNIMED, " +
                "AMIL from new_medico WHERE Usuario='" +
                HelloApplication.Loginsusuario.get(HelloApplication.Loginsusuario.size() - 1).getUsuario() + "'";
        try {
            Statement statementDados = connectDB2.createStatement();
            ResultSet resultadoDados = statementDados.executeQuery(mostrarDados);
            while (resultadoDados.next()) {
                nome.setText(resultadoDados.getString(1));
                usuario.setText(resultadoDados.getString(2));
                EspecialidadeEscolhida.setText(resultadoDados.getString(3));
                Senha1.setText(resultadoDados.getString(4));
                if(resultadoDados.getString("CAMED").equals("true")){
                    CAMED.setSelected(true);
                } if(resultadoDados.getString("BRADESCO").equals("true")){
                    BRADESCO.setSelected(true);
                } if(resultadoDados.getString("UNIMED").equals("true")){
                    UNIMED.setSelected(true);
                } if(resultadoDados.getString("AMIL").equals("true")){
                    AMIL.setSelected(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        ObservableList<String> ListaDeEspecialidades = FXCollections.observableArrayList("CLINICA GERAL",
                "GINECOLOGIA", "ORTOPEDIA", "DERMATOLOGIA","UROLOGIA", "OFTALMOLOGIA", "NEUROLOGIA", "CARDIOLOGIA");
        CBEspecialidades.setItems(ListaDeEspecialidades);
    }

    @FXML
    public void SelecionarPlano(ActionEvent event){
        String plano = CBEspecialidades.getSelectionModel().getSelectedItem().toString();
        EspecialidadeEscolhida.setText(plano);
    }
}
