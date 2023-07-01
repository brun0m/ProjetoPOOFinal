package com.example.teste123;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConsultaMedicoController {
    public String nomemedico;
    public String usuariomedico;
    @FXML
    private Button ButtonEnviar;
    @FXML
    private Button ButtonVoltar;
    @FXML
    private TextArea InstrucoesMed;
    @FXML
    private Label Assinatura;
    @FXML
    private Label mensagemRegistrar;

    public void ButtonVoltarAnteriorOnAction(ActionEvent event){
        try {
            Usuario user = new Usuario(getUsuariomedico());
            HelloApplication.Loginsusuario.add(user);
            Stage stage = (Stage) ButtonVoltar.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("menumedico-view.fxml"));
            Stage MenuStage = new Stage();
            MenuStage.initStyle(StageStyle.UNDECORATED);
            MenuStage.setScene(new Scene(root, 640, 360));
            MenuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void ButtonEnviarPacienteOnAction(ActionEvent event){
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();

        String Consultando = "INSERT INTO new_consulta(Estrelas, Usuario, UsuarioMed, Atendimento, NomeMed," +
                "Preco, Encerrou) VALUES ('" + 0 + "','" + getUsuariomedico() + "','" + getNomemedico() + "','" +
                InstrucoesMed.getText() + "','" + getNomemedico() + "','" + 0 + "','false')";
        try{
            Statement statement = connectDB2.createStatement();
            statement.executeUpdate(Consultando);
            mensagemRegistrar.setText("Instruções mandadas com sucesso!");
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void initialize(){
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();
        mensagemRegistrar.setText("");
        String mostrarDados = "SELECT Nome, Usuario from new_medico WHERE Usuario='" +
                HelloApplication.Loginsusuario.get(HelloApplication.Loginsusuario.size()-1).getUsuario() + "'";

        try{
            Statement statementDados = connectDB2.createStatement();
            ResultSet resultadoDados = statementDados.executeQuery(mostrarDados);
            while(resultadoDados.next()) {
                setNomemedico(resultadoDados.getString("Nome"));
                setUsuariomedico(resultadoDados.getString("Usuario"));
            }
            Assinatura.setText(getNomemedico());
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public String getNomemedico() {
        return nomemedico;
    }

    public void setNomemedico(String nomemedico) {
        this.nomemedico = nomemedico;
    }

    public String getUsuariomedico() {
        return usuariomedico;
    }

    public void setUsuariomedico(String usuariomedico) {
        this.usuariomedico = usuariomedico;
    }
}
