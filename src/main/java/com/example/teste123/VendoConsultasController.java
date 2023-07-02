package com.example.teste123;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import java.util.Calendar;
import java.time.LocalDate;

import java.sql.*;

public class VendoConsultasController{
    @FXML
    private Label UsuarioMenu, Aviso;
    @FXML
    private Label UsuarioPlano;
    @FXML
    private Button VoltarMenu;
    @FXML
    private TextField Pesquisando;
    @FXML
    private TableView<VerConsultaModelo> ConsultasTableView;
    @FXML
    private TableColumn<VerConsultaModelo, String> ID;
    @FXML
    private TableColumn<VerConsultaModelo, String> UsuarioTabela;
    @FXML
    private TableColumn<VerConsultaModelo, String> NomeTabela;
    @FXML
    private TableColumn<VerConsultaModelo, String> EspecialidadeTabela;
    @FXML
    private TableColumn<VerConsultaModelo, String> DataTabela;
    @FXML
    private TableColumn<VerConsultaModelo, String> CancelarTabela;
    @FXML
    private TableColumn<VerConsultaModelo, String> VerTabela;
    String query = null;
    VerConsultaModelo UsuarioMed = null;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    int pos;
    String dataagenda = null;

    ObservableList<VerConsultaModelo> mostrandoConsultasObservableList = FXCollections.observableArrayList();

    @FXML
    public void BotaoVoltarMenuOnAction(ActionEvent event) {
        try {
            Usuario user = new Usuario(UsuarioMenu.getText());
            HelloApplication.Loginsusuario.add(user);
            Stage stage = (Stage) VoltarMenu.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("menu-view.fxml"));
            Stage MenuStage = new Stage();
            MenuStage.initStyle(StageStyle.UNDECORATED);
            MenuStage.setScene(new Scene(root, 640, 360));
            MenuStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void initialize() {
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();

        String mostrarDados = "SELECT Usuario, Plano from new_table WHERE Usuario='" +
                HelloApplication.Loginsusuario.get(HelloApplication.Loginsusuario.size() - 1).getUsuario() + "'";
        UsuarioMenu.setText(HelloApplication.Loginsusuario.get(HelloApplication.Loginsusuario.size() - 1).getUsuario());
        try {
            Statement statementDados = connectDB2.createStatement();
            ResultSet resultadoDados = statementDados.executeQuery(mostrarDados);
            while (resultadoDados.next()) {
                UsuarioPlano.setText(resultadoDados.getString("Plano"));
            }
            String mostrarMedico = "SELECT idnew_table, UsuarioMedico, NomeMedico, Especialidade, Data, Posicao from new_agendamento WHERE " +
                     "UsuarioPaciente ='" + UsuarioMenu.getText() + "' and Encerrou='false'";
            try {
                Statement statementMedicos = connectDB2.createStatement();
                ResultSet resultadoMedicos = statementMedicos.executeQuery(mostrarMedico);
                while (resultadoMedicos.next()) {
                    int ID = resultadoMedicos.getInt("idnew_table");
                    String Usuario = resultadoMedicos.getString("UsuarioMedico");
                    String Nome = resultadoMedicos.getString("NomeMedico");
                    String Especialidade = resultadoMedicos.getString("Especialidade");
                    String Data = resultadoMedicos.getString("Data");
                    int Posicao = resultadoMedicos.getInt("Posicao");
                    mostrandoConsultasObservableList.add(new VerConsultaModelo(ID, Usuario, Nome, Especialidade, Data, Posicao));
                }
                //
                ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
                UsuarioTabela.setCellValueFactory(new PropertyValueFactory<>("Usuario"));
                NomeTabela.setCellValueFactory(new PropertyValueFactory<>("Nome"));
                EspecialidadeTabela.setCellValueFactory(new PropertyValueFactory<>("Especialidade"));
                DataTabela.setCellValueFactory(new PropertyValueFactory<>("Data"));

                //

                carregarAcao();
                carregarAcao2();
                ConsultasTableView.setItems(mostrandoConsultasObservableList);

                FilteredList<VerConsultaModelo> filteredData = new FilteredList<>(mostrandoConsultasObservableList, b -> true);

                Pesquisando.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate(VerConsultaModelo -> {
                        if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                            return true;
                        }
                        String novaPesquisa = newValue.toLowerCase();

                        if (VerConsultaModelo.getNome().toLowerCase().indexOf(novaPesquisa) > -1) {
                            return true;
                        } else if (VerConsultaModelo.getEspecialidade().toLowerCase().indexOf(novaPesquisa) > -1) {
                            return true;
                        } else if(VerConsultaModelo.getData().toLowerCase().indexOf(novaPesquisa) > -1){
                            return true;
                        }
                        else return false;

                    });
                });
                //
                SortedList<VerConsultaModelo> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(ConsultasTableView.comparatorProperty());
                ConsultasTableView.setItems(sortedData);
                //
            } catch (SQLException e) {
                Logger.getLogger(VerConsultaModelo.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void resetaTabela(){
        try {
            DataBaseConexao conectarAgora2 = new DataBaseConexao();
            Connection connectDB2 = conectarAgora2.getConnection();
            mostrandoConsultasObservableList.clear();

            query = "SELECT idnew_table, UsuarioMedico, NomeMedico, Especialidade, Data, Posicao from new_agendamento WHERE " +
                    "UsuarioPaciente ='" + UsuarioMenu.getText() + "'";
            preparedStatement = connectDB2.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                mostrandoConsultasObservableList.add(new  VerConsultaModelo(
                        resultSet.getInt("idnew_table"),
                        resultSet.getString("UsuarioMedico"),
                        resultSet.getString("NomeMedico"),
                        resultSet.getString("Especialidade"),
                        resultSet.getString("Data"),
                        resultSet.getInt("Posicao")));
                ConsultasTableView.setItems(mostrandoConsultasObservableList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendoConsultasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void carregarAcao(){
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();
        //add cell of button edit
        Callback<TableColumn<VerConsultaModelo, String>, TableCell<VerConsultaModelo, String>> cellFoctory = (TableColumn<VerConsultaModelo, String> param) -> {
            // make cell containing buttons
            final TableCell<VerConsultaModelo, String> cell = new TableCell<VerConsultaModelo, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button irConsulta = new Button("✓");
                        setGraphic(irConsulta);

                        irConsulta.setStyle(
                                "-fx-alignment:center;" +
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-background-color:#00E676;"
                        );

                        irConsulta.setOnMouseClicked((MouseEvent event) -> {

                            UsuarioMed = ConsultasTableView.getSelectionModel().getSelectedItem();
                            String confirma = "SELECT Posicao, Data from new_agendamento WHERE idnew_table=" + UsuarioMed.getID();
                            try {
                                LocalDate today = LocalDate.now();
                                Statement statementDados = connectDB2.createStatement();
                                ResultSet resultadoDados = statementDados.executeQuery(confirma);
                                while(resultadoDados.next()) {
                                    pos = resultadoDados.getInt("Posicao");
                                    dataagenda = resultadoDados.getString("Data");
                                }
                                if(dataagenda.equals(String.valueOf(today)) == false){
                                    Aviso.setText("Espere chegar o dia da consulta!");
                                }
                                else if(pos < 4) {
                                    Usuario user = new Usuario(UsuarioMenu.getText());
                                    ListaID id1 = new ListaID(UsuarioMed.getID());
                                    HelloApplication.Loginsusuario.add(user);
                                    HelloApplication.ListaID.add(id1);
                                    Stage stage = (Stage) irConsulta.getScene().getWindow();
                                    stage.close();
                                    Parent root = FXMLLoader.load(getClass().getResource("Consulta-view.fxml"));
                                    Stage AlterarStage = new Stage();
                                    AlterarStage.initStyle(StageStyle.UNDECORATED);
                                    AlterarStage.setScene(new Scene(root, 600, 400));
                                    AlterarStage.show();
                                }
                                else {
                                    Aviso.setText("Você está na lista de espera dessa consulta!");
                                }
                            } catch (SQLException | IOException e) {
                                Logger.getLogger(VendoConsultasController.class.getName()).log(Level.SEVERE, null, e);
                                e.printStackTrace();
                                e.getCause();
                            }
                        });
                        setText(null);
                    }
                }
            };
            return cell;
        };
        VerTabela.setCellFactory(cellFoctory);
    }

    public void carregarAcao2(){
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();
        //add cell of button edit
        Callback<TableColumn<VerConsultaModelo, String>, TableCell<VerConsultaModelo, String>> cellFoctory = (TableColumn<VerConsultaModelo, String> param) -> {
            // make cell containing buttons
            final TableCell<VerConsultaModelo, String> cell = new TableCell<VerConsultaModelo, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button deleteIcon = new Button("X");

                        setGraphic(deleteIcon);

                        deleteIcon.setStyle(
                                "-fx-alignment:center;" +
                                        " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-background-color:#ff1744;"
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            UsuarioMed = ConsultasTableView.getSelectionModel().getSelectedItem();
                            String pegarpos = "SELECT Posicao FROM new_agendamento WHERE idnew_table='" +
                                    UsuarioMed.getID() + "'";
                            try {
                                Statement statementDados = connectDB2.createStatement();
                                ResultSet resultadoDados = statementDados.executeQuery(pegarpos);
                                while(resultadoDados.next()) {
                                    pos = resultadoDados.getInt("Posicao");
                                }
                                String mudandoFila = "UPDATE new_agendamento SET Posicao=Posicao-1 WHERE " +
                                        "idnew_table ='" + UsuarioMed.getID() + "' and Posicao> " + pos;
                                Statement statement = connectDB2.createStatement();
                                statement.executeUpdate(mudandoFila);
                                query = "DELETE FROM new_agendamento WHERE idnew_table=" + UsuarioMed.getID();
                                preparedStatement = connectDB2.prepareStatement(query);
                                preparedStatement.execute();
                                resetaTabela();
                            } catch (SQLException e){
                                Logger.getLogger(VendoConsultasController.class.getName()).log(Level.SEVERE, null, e);
                            }
                        });
                        setText(null);
                    }
                }
            };
            return cell;
        };
        CancelarTabela.setCellFactory(cellFoctory);
    }
}
