package com.example.teste123;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoricoMedicoController {
    private String UsuarioMenu;
    @FXML
    private Button BotaoVoltar;
    @FXML
    private TextField Pesquisando;
    @FXML
    private TableView<HistoricoModelo> HistoricoTableView;
    @FXML
    private TableColumn<HistoricoModelo, String> NomeTabela;
    @FXML
    private TableColumn<HistoricoModelo, String> EstrelaTabela;
    @FXML
    private TableColumn<HistoricoModelo, String> ComentarioTabela;

    ObservableList<HistoricoModelo> verHistoricoObservableList = FXCollections.observableArrayList();

    @FXML
    public void ButtonVoltarOnAction(ActionEvent event){
        try {
            Usuario user = new Usuario(getUsuarioMenu());
            HelloApplication.Loginsusuario.add(user);
            Stage stage = (Stage) BotaoVoltar.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("menumedico-view.fxml"));
            Stage AgendarStage = new Stage();
            AgendarStage.initStyle(StageStyle.UNDECORATED);
            AgendarStage.setScene(new Scene(root, 640, 480));
            AgendarStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void initialize(){
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();
        setUsuarioMenu(HelloApplication.Loginsusuario.get(HelloApplication.Loginsusuario.size()-1).getUsuario());

        String mostrarDados = "SELECT NomeUsuario, Avaliacao, Estrelas, Data from new_agendamento WHERE UsuarioMedico='" +
                getUsuarioMenu() + "' and Encerrou='true'";
        try{
            Statement statementMedicos = connectDB2.createStatement();
            ResultSet resultadoMedicos = statementMedicos.executeQuery(mostrarDados);
            while(resultadoMedicos.next()){
                String Nome = resultadoMedicos.getString("NomeUsuario");
                int Estrelas = resultadoMedicos.getInt("Estrelas");
                String Avaliacao = resultadoMedicos.getString("Avaliacao");
                String Data = resultadoMedicos.getString("Data");
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate data = LocalDate.parse(Data, formato);
                LocalDate today = LocalDate.now();
                if(data.compareTo(today) > -7) {
                    verHistoricoObservableList.add(new HistoricoModelo(Nome, Estrelas, Avaliacao));
                } else {}
            }
            //
            NomeTabela.setCellValueFactory(new PropertyValueFactory<>("Nome"));
            EstrelaTabela.setCellValueFactory(new PropertyValueFactory<>("Estrelas"));
            ComentarioTabela.setCellValueFactory(new PropertyValueFactory<>("Comentario"));
            //
            HistoricoTableView.setItems(verHistoricoObservableList);

            FilteredList<HistoricoModelo> filteredData = new FilteredList<>(verHistoricoObservableList, b -> true);

            Pesquisando.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(HistoricoModelo -> {
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }
                    String novaPesquisa = newValue.toLowerCase();

                    if (HistoricoModelo.getNome().toLowerCase().indexOf(novaPesquisa) > -1) {
                        return true;
                    } else if(HistoricoModelo.getEstrelas() > -1){
                        return true;
                    } else if(HistoricoModelo.getComentario().toLowerCase().indexOf(novaPesquisa) > -1){
                        return true;
                    }
                    else
                        return false;
                });
            });
            //
            SortedList<HistoricoModelo> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(HistoricoTableView.comparatorProperty());
            HistoricoTableView.setItems(sortedData);
            //
        }catch (SQLException e){
            Logger.getLogger(HistoricoController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }


    public String getUsuarioMenu() {
        return UsuarioMenu;
    }

    public void setUsuarioMenu(String usuarioMenu) {
        UsuarioMenu = usuarioMenu;
    }
}
