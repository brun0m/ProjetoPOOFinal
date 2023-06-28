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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class pesquisaController{
    @FXML
    private Label UsuarioMenu;
    @FXML
    private Label UsuarioPlano;
    @FXML
    private Button VoltarMenu;
    @FXML
    private TextField Pesquisando;
    @FXML
    private TableView<pesquisarModelo> MedicoTableView;
    @FXML
    private TableColumn<pesquisarModelo, String> NomeTabela;
    @FXML
    private TableColumn<pesquisarModelo, String> EspecTabela;
    @FXML
    private TableColumn<pesquisarModelo, Double> EstrelaTabela;
    @FXML
    private TableColumn<pesquisarModelo, String> AvaliacaoTabela;

    ObservableList<pesquisarModelo> pesquisarModelaObservableList = FXCollections.observableArrayList();

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
    public void initialize(){
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();

        String mostrarDados = "SELECT Usuario, Plano from new_table WHERE Usuario='" +
                HelloApplication.Loginsusuario.get(HelloApplication.Loginsusuario.size()-1).getUsuario() + "'";
        UsuarioMenu.setText(HelloApplication.Loginsusuario.get(HelloApplication.Loginsusuario.size()-1).getUsuario());
        try{
            Statement statementDados = connectDB2.createStatement();
            ResultSet resultadoDados = statementDados.executeQuery(mostrarDados);
            while(resultadoDados.next()){
                UsuarioPlano.setText(resultadoDados.getString("Plano"));
            }
            String mostrarMedico = "SELECT Nome, Especialidade, Avaliacao, UltimasAvaliacao from new_medico WHERE " +
                    UsuarioPlano.getText() + "='true'";
            try{
                Statement statementMedicos = connectDB2.createStatement();
                ResultSet resultadoMedicos = statementMedicos.executeQuery(mostrarMedico);
                while(resultadoMedicos.next()){
                    String Nome = resultadoMedicos.getString("Nome");
                    String Especialidade = resultadoMedicos.getString("Especialidade");
                    Double Estrelas = resultadoMedicos.getDouble("Avaliacao");
                    String Avaliacao = resultadoMedicos.getString("UltimasAvaliacao");

                    pesquisarModelaObservableList.add(new pesquisarModelo(Nome, Especialidade, Estrelas, Avaliacao));
                }
                //
                NomeTabela.setCellValueFactory(new PropertyValueFactory<>("Nome"));
                EspecTabela.setCellValueFactory(new PropertyValueFactory<>("Especialidade"));
                EstrelaTabela.setCellValueFactory(new PropertyValueFactory<>("Estrelas"));
                AvaliacaoTabela.setCellValueFactory(new PropertyValueFactory<>("Avaliacao"));
                //
                MedicoTableView.setItems(pesquisarModelaObservableList);

                FilteredList<pesquisarModelo> filteredData = new FilteredList<>(pesquisarModelaObservableList, b -> true);

                Pesquisando.textProperty().addListener((observable, oldValue, newValue) -> {
                        filteredData.setPredicate(pesquisarModelo -> {
                            if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                                return true;
                            }
                            String novaPesquisa = newValue.toLowerCase();

                            if (pesquisarModelo.getNome().toLowerCase().indexOf(novaPesquisa) > -1) {
                                return true;
                            } else if(pesquisarModelo.getEspecialidade().toLowerCase().indexOf(novaPesquisa) > -1){
                                return true;
                            }
                            else
                                return false;

                        });
                });
                //
                SortedList<pesquisarModelo> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(MedicoTableView.comparatorProperty());
                MedicoTableView.setItems(sortedData);
                //
            }catch (SQLException e){
                Logger.getLogger(pesquisarModelo.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

}
