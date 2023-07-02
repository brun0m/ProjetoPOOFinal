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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultaMedicoController {
    public String nomemedico;
    public String usuariomedico;
    public String usuariopaciente;
    public String nomepaciente;
    public int ID;
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

        String Consultando = "UPDATE new_agendamento set Atendimento='" + InstrucoesMed.getText() + "' " +
                "WHERE UsuarioMedico='" + getUsuariomedico() + "' and idnew_table='" + getID() + "'";
        try{
            Statement statement = connectDB2.createStatement();
            statement.executeUpdate(Consultando);
            mensagemRegistrar.setText("Instruções mandadas com sucesso!");
        } catch(SQLException e){
            Logger.getLogger(VerConsultaModelo.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void initialize(){
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();
        mensagemRegistrar.setText("");
        String pegarDados = "SELECT NomeMedico, UsuarioPaciente, UsuarioMedico, UsuarioMedico, idnew_table from new_agendamento WHERE idnew_table =" +
                HelloApplication.ListaID.get(HelloApplication.ListaID.size()-1).getID();

        try{
            Statement statementDados = connectDB2.createStatement();
            ResultSet resultadoDados = statementDados.executeQuery(pegarDados);
            while(resultadoDados.next()) {
                setNomemedico(resultadoDados.getString("NomeMedico"));
                setUsuariomedico(resultadoDados.getString("UsuarioMedico"));
                setID(resultadoDados.getInt("idnew_table"));
                setUsuariopaciente(resultadoDados.getString("UsuarioPaciente"));
            }
            Assinatura.setText(getNomemedico());

        }catch(SQLException e){
            Logger.getLogger(VerConsultaModelo.class.getName()).log(Level.SEVERE, null, e);
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsuariopaciente() {
        return usuariopaciente;
    }

    public void setUsuariopaciente(String usuariopaciente) {
        this.usuariopaciente = usuariopaciente;
    }

    public String getNomepaciente() {
        return nomepaciente;
    }

    public void setNomepaciente(String nomepaciente) {
        this.nomepaciente = nomepaciente;
    }
}
