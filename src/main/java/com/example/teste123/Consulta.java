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

public class Consulta {
    private String nomePaciente;
    private String usuarioPaciente;
    private String usuarioMedico;
    private int ID;
    private int nota=0;
    @FXML
    private TextArea Avaliacao;
    @FXML
    private Button ButtonConfirmar;
    @FXML
    private Button Button1;
    @FXML
    private Button Button2;
    @FXML
    private Button Button3;
    @FXML
    private Button Button4;
    @FXML
    private Button Button5;
    @FXML
    private Button ButtonVoltar;
    @FXML
    private Label Plano;
    @FXML
    private TextArea InstrucoesMed;
    @FXML
    private Label Assinatura;
    @FXML
    private Label Preco;
    @FXML
    private Label Aviso, estrelas;
    int num;
    String UltimasAvaliacao = null;
    double Avaliacao1;
    @FXML
    public void Butto1EstrelaOnAction(ActionEvent event){
        setNota(1);
        estrelas.setText("★");
    }
    @FXML
    public void Butto2EstrelaOnAction(ActionEvent event){
        setNota(2);
        estrelas.setText("★★");
    }
    @FXML
    public void Butto3EstrelaOnAction(ActionEvent event){
        setNota(3);
        estrelas.setText("★★★");
    }
    @FXML
    public void Butto4EstrelaOnAction(ActionEvent event){
        setNota(4);
        estrelas.setText("★★★★");
    }
    @FXML
    public void Butto5EstrelaOnAction(ActionEvent event){
        setNota(5);
        estrelas.setText("★★★★★");
    }

    @FXML
    public void ButtonConfirmarOnAction(ActionEvent event){
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();
        String mudarDados = "UPDATE new_agendamento SET Encerrou='true', Avaliacao='" + Avaliacao.getText() + "', Estrelas='"
                + getNota() + "' WHERE idnew_table='" + getID() + "'";
        try {
            Statement statement = connectDB2.createStatement();
            statement.executeUpdate(mudarDados);
        }catch (Exception e){
            e.printStackTrace();
        }

        String atualizandoMedico = "SELECT NumDeAvaliacao, Avaliacao, UltimasAvaliacao" +
        " from new_medico WHERE Usuario='" + getUsuarioMedico() + "'";
        try{
            Statement statementDados2 = connectDB2.createStatement();
            ResultSet resultadoDados2 = statementDados2.executeQuery(atualizandoMedico);
            while(resultadoDados2.next()) {
                num = resultadoDados2.getInt("NumDeAvaliacao");
                UltimasAvaliacao = resultadoDados2.getString("UltimasAvaliacao");
                Avaliacao1 = resultadoDados2.getDouble("Avaliacao");
            }
            if(Avaliacao.getText() != null) {
                UltimasAvaliacao = UltimasAvaliacao + " /// " + Avaliacao.getText();
            } if(getNota() != 0){
                num++;
                Avaliacao1 = (Avaliacao1 + getNota()) / num;
            }

            String attMedico2 = "UPDATE new_medico SET NumDeAvaliacao='" + num + "', Avaliacao='" + Avaliacao1 +
                    "', UltimasAvaliacao='" + UltimasAvaliacao + "' WHERE Usuario='" +
                    getUsuarioMedico() + "'";
            try {
                Statement statement = connectDB2.createStatement();
                statement.executeUpdate(attMedico2);
                Aviso.setText("Consulta Realizada com sucesso!");
                ButtonConfirmar.setDisable(true);
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void ButtonVoltarOnAction(ActionEvent event){
        try {
            Aviso.setText("");
            Usuario user = new Usuario(getUsuarioPaciente());
            HelloApplication.Loginsusuario.add(user);
            Stage stage = (Stage) ButtonVoltar.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("menu-view.fxml"));
            Stage AlterarStage = new Stage();
            AlterarStage.initStyle(StageStyle.UNDECORATED);
            AlterarStage.setScene(new Scene(root, 640, 360));
            AlterarStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void initialize(){
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();
        setID(HelloApplication.ListaID.get(HelloApplication.ListaID.size()-1).getID());
        String pegandoDados = "SELECT UsuarioMedico, NomeMedico, UsuarioPaciente, NomeUsuario, Plano, Atendimento, Preco" +
                " from new_agendamento WHERE idnew_table =" + getID() + "";

        try{
            Statement statementDados = connectDB2.createStatement();
            ResultSet resultadoDados = statementDados.executeQuery(pegandoDados);
            while(resultadoDados.next()) {
                Assinatura.setText(resultadoDados.getString("NomeMedico"));
                InstrucoesMed.setText(resultadoDados.getString("Atendimento"));
                Plano.setText(resultadoDados.getString("Plano"));
                Preco.setText(resultadoDados.getString("Preco"));
                setUsuarioPaciente(resultadoDados.getString("UsuarioPaciente"));
                setUsuarioMedico(resultadoDados.getString("UsuarioMedico"));
                setNomePaciente(resultadoDados.getString("NomeUsuario"));
            }
        }catch(SQLException e){
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            e.getCause();
        }
        verificarAtendimento();
    }

    public void verificarAtendimento(){
        if (InstrucoesMed.getText().equals("")){
            ButtonConfirmar.setDisable(true);
            Aviso.setText("Espere o médico enviar as instruções para poder confirmar");

        } else {
            ButtonConfirmar.setDisable(false);
            Aviso.setText("");
        }
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getUsuarioPaciente() {
        return usuarioPaciente;
    }

    public void setUsuarioPaciente(String usuarioPaciente) {
        this.usuarioPaciente = usuarioPaciente;
    }

    public String getUsuarioMedico() {
        return usuarioMedico;
    }

    public void setUsuarioMedico(String usuarioMedico) {
        this.usuarioMedico = usuarioMedico;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
