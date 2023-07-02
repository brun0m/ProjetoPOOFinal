package com.example.teste123;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DermatologiaController {
    private double Preco;
    private int cont;
    private String data;
    private String nomeusu;
    private String medUsu;
    @FXML
    private Button Cancelar;
    @FXML
    private Label Medicoescolhido;
    @FXML
    private ComboBox<String> escolherMedico;
    @FXML
    private TextField Pesquisar;
    @FXML
    private DatePicker escolherData;
    @FXML
    private Label Usuario;
    @FXML
    private Label ConfirmaAgendamento;
    @FXML
    private Label UsuarioPlano;


    ObservableList<String> escolherMedicoObservableList = FXCollections.observableArrayList();
    public DermatologiaController(){}

    @FXML
    public void ConfirmarAgendamentoOnAction(ActionEvent event){
        if(Medicoescolhido.getText().isBlank() == false && getData()!=null){
            validarAgendamento();
        } else{
            ConfirmaAgendamento.setText("Escolha um médico e uma data para agendar!");
        }

    }

    @FXML
    public void BotaoSairAgendamentoOnAction(ActionEvent event){
        try {
            Usuario user = new Usuario(Usuario.getText());
            HelloApplication.Loginsusuario.add(user);
            Stage stage = (Stage) Cancelar.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("agendarconsulta-view.fxml"));
            Stage AgendarStage = new Stage();
            AgendarStage.initStyle(StageStyle.UNDECORATED);
            AgendarStage.setScene(new Scene(root, 800, 600));
            AgendarStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void SelecionarMedico(ActionEvent event){
        String esp = escolherMedico.getSelectionModel().getSelectedItem().toString();
        Medicoescolhido.setText(esp);
    }

    @FXML
    public void SelecionarData(ActionEvent event){
        String data = escolherData.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        setData(data);
    }

    @FXML
    public void initialize(){
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();

        String mostrarDados = "SELECT Usuario, Plano from new_table WHERE Usuario='" +
                HelloApplication.Loginsusuario.get(HelloApplication.Loginsusuario.size()-1).getUsuario() + "'";

        try{
            Statement statementDados = connectDB2.createStatement();
            ResultSet resultadoDados = statementDados.executeQuery(mostrarDados);
            while(resultadoDados.next()) {
                Usuario.setText(resultadoDados.getString("Usuario"));
                UsuarioPlano.setText(resultadoDados.getString("Plano"));
            }
            if(UsuarioPlano.getText().equals("SEMPLANO")){
                setPreco(200);
            } else {setPreco(0);}
            String mostrandoMedicos = "SELECT Nome, Usuario from new_medico WHERE " +
                    UsuarioPlano.getText() + "='true' and Especialidade='DERMATOLOGIA'";
            try{
                Statement statementMedicos = connectDB2.createStatement();
                ResultSet resultadoMedicos = statementMedicos.executeQuery(mostrandoMedicos);
                while(resultadoMedicos.next()){
                    String nome = resultadoMedicos.getString("Nome");
                    String usuario = resultadoMedicos.getString("Usuario");
                    escolherMedicoObservableList.add(nome);
                }

                escolherMedico.setItems(escolherMedicoObservableList);

                escolherData.setDayCellFactory(picker -> new DateCell(){
                    public void updateItem(LocalDate date, boolean empty){
                        super.updateItem(date, empty);
                        LocalDate today = LocalDate.now();
                        setDisable(empty || date.compareTo(today) < 0);

                    }
                });

            }catch (SQLException e){
                Logger.getLogger(escolherMedicoModelo.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void validarAgendamento(){
        DataBaseConexao conectarAgora = new DataBaseConexao();
        Connection connectDB2 = conectarAgora.getConnection();

        String mostrarMed = "SELECT Usuario from new_medico WHERE Nome='" +
                Medicoescolhido.getText() + "' and Especialidade='DERMATOLOGIA' and " + UsuarioPlano.getText() + "='true'";
        String mostrarUsu = "SELECT Nome from new_table WHERE Usuario='" + Usuario.getText() + "'";
        try{
            Statement statementDados = connectDB2.createStatement();
            ResultSet resultadoDados = statementDados.executeQuery(mostrarMed);
            while(resultadoDados.next()) {
                setMedUsu(resultadoDados.getString("Usuario"));
            }
            Statement statementDados2 = connectDB2.createStatement();
            ResultSet resultadoDados2 = statementDados2.executeQuery(mostrarUsu);
            while(resultadoDados2.next()) {
                setNomeusu(resultadoDados2.getString("Nome"));
            }
            String verAgendamento = "SELECT Posicao from new_agendamento WHERE Especialidade='DERMATOLOGIA' and " +
                    "UsuarioMedico='" + getMedUsu() + "' and Data='" + getData() + "' and Encerrou='false'";
            try{
                Statement statementAgenda = connectDB2.createStatement();
                ResultSet resultadoAgenda = statementAgenda.executeQuery(verAgendamento);
                cont = 0;
                while(resultadoAgenda.next()){
                    cont++;
                }
                if(cont >= 4){
                    var alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Aviso");
                    alert.setHeaderText("Agenda do médico pro dia está cheio");
                    alert.setContentText("Você deseja entrar na lista de espera?");
                    alert.showAndWait().ifPresent((btnType) -> {
                        if (btnType == ButtonType.YES) {
                            BotarAgenda();
                        } else if (btnType == ButtonType.NO) {
                        }
                    });
                }else {
                    BotarAgenda();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void BotarAgenda(){
        DataBaseConexao conectarAgora = new DataBaseConexao();
        Connection connectDB2 = conectarAgora.getConnection();
        String novoAgendamento = "INSERT INTO new_agendamento(UsuarioMedico, UsuarioPaciente, Preco, " +
                "Especialidade, Data, Posicao, NomeMedico, NomeUsuario, Estrelas, Encerrou, Avaliacao, Plano, Atendimento) VALUES (";
        String novoAgendamento2 = "'" + getMedUsu() + "','" + Usuario.getText() + "','" + getPreco() + "','DERMATOLOGIA','"
                + getData() + "','" + cont+1 + "','" + Medicoescolhido.getText() + "','" + getNomeusu() + "','" +
                0 + "','false','','" + UsuarioPlano.getText() + "','')";
        String addAgendamento = novoAgendamento + novoAgendamento2;
        try{
            Statement statement = connectDB2.createStatement();
            statement.executeUpdate(addAgendamento);
            ConfirmaAgendamento.setText("Agendamento Confirmado!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMedUsu() {
        return medUsu;
    }

    public void setMedUsu(String medUsu) {
        this.medUsu = medUsu;
    }

    public String getNomeusu() {
        return nomeusu;
    }

    public void setNomeusu(String nomeusu) {
        this.nomeusu = nomeusu;
    }

    public double getPreco() {
        return Preco;
    }

    public void setPreco(double preco) {
        Preco = preco;
    }
}
