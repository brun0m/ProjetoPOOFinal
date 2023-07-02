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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class verConsultarMedicoController {
    private String NomeMed;
    private String UserMed;
    @FXML
    private Button VoltarMenu;
    @FXML
    private TextField Pesquisando;
    @FXML
    private TableView<ConsultaMedicoModelo> ConsultasTableView;
    @FXML
    private TableColumn<ConsultaMedicoModelo, String> ID;
    @FXML
    private TableColumn<ConsultaMedicoModelo, String> UsuarioTabela;
    @FXML
    private TableColumn<ConsultaMedicoModelo, String> NomeTabela;
    @FXML
    private TableColumn<ConsultaMedicoModelo, String> DataTabela;
    @FXML
    private TableColumn<ConsultaMedicoModelo, String> CancelarTabela;
    @FXML
    private TableColumn<ConsultaMedicoModelo, String> VerTabela;
    String query = null;
    ConsultaMedicoModelo UsuarioMed = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;

    ObservableList<ConsultaMedicoModelo> mostrandoConsultasObservableList = FXCollections.observableArrayList();

    @FXML
    public void BotaoVoltarMenuOnAction(ActionEvent event) {
        try {
            Usuario user = new Usuario(getUserMed());
            HelloApplication.Loginsusuario.add(user);
            Stage stage = (Stage) VoltarMenu.getScene().getWindow();
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

    @FXML
    public void initialize() {
        DataBaseConexao conectarAgora2 = new DataBaseConexao();
        Connection connectDB2 = conectarAgora2.getConnection();
        setUserMed(HelloApplication.Loginsusuario.get(HelloApplication.Loginsusuario.size() - 1).getUsuario());
        String mostrarDados = "SELECT Nome from new_medico WHERE Usuario='" + getUserMed() + "'";

        try {
            Statement statementDados = connectDB2.createStatement();
            ResultSet resultadoDados = statementDados.executeQuery(mostrarDados);
            while (resultadoDados.next()) {
                setNomeMed(resultadoDados.getString("Nome"));
            }
            String mostrarMedico = "SELECT idnew_table, UsuarioPaciente, NomeUsuario, Data from new_agendamento WHERE " +
                    "UsuarioMedico ='" + getUserMed() + "' and Encerrou='false'";
            try {
                Statement statementMedicos = connectDB2.createStatement();
                ResultSet resultadoMedicos = statementMedicos.executeQuery(mostrarMedico);
                while (resultadoMedicos.next()) {
                    int ID = resultadoMedicos.getInt("idnew_table");
                    String Usuario = resultadoMedicos.getString("UsuarioPaciente");
                    String Nome = resultadoMedicos.getString("NomeUsuario");
                    String Data = resultadoMedicos.getString("Data");
                    mostrandoConsultasObservableList.add(new ConsultaMedicoModelo(ID, Usuario, Nome, Data));
                }
                //
                UsuarioTabela.setCellValueFactory(new PropertyValueFactory<>("Usuario"));
                NomeTabela.setCellValueFactory(new PropertyValueFactory<>("Nome"));
                DataTabela.setCellValueFactory(new PropertyValueFactory<>("Data"));
                ID.setCellValueFactory(new PropertyValueFactory<>("ID"));

                //
                carregarAcao();
                carregarAcao2();
                ConsultasTableView.setItems(mostrandoConsultasObservableList);

                FilteredList<ConsultaMedicoModelo> filteredData = new FilteredList<>(mostrandoConsultasObservableList, b -> true);

                Pesquisando.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate(ConsultaMedicoModelo -> {
                        if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                            return true;
                        }
                        String novaPesquisa = newValue.toLowerCase();

                        if (ConsultaMedicoModelo.getNome().toLowerCase().indexOf(novaPesquisa) > -1) {
                            return true;
                        } else if(ConsultaMedicoModelo.getUsuario().toLowerCase().indexOf(novaPesquisa) > -1){
                            return true;
                        }
                        else if(ConsultaMedicoModelo.getData().toLowerCase().indexOf(novaPesquisa) > -1){
                            return true;
                        }
                        else return false;

                    });
                });
                //
                SortedList<ConsultaMedicoModelo> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(ConsultasTableView.comparatorProperty());
                ConsultasTableView.setItems(sortedData);
                //
            } catch (SQLException e) {
                Logger.getLogger(verConsultarMedicoController.class.getName()).log(Level.SEVERE, null, e);
                e.printStackTrace();
            } catch(java.lang.IllegalStateException e){
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
            query = "SELECT idnew_table, UsuarioMedico, NomeMedico, Especialidade, Data from new_agendamento WHERE " +
                    "UsuarioPaciente ='" + getUserMed() + "'";
            preparedStatement = connectDB2.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                mostrandoConsultasObservableList.add(new ConsultaMedicoModelo(
                        resultSet.getInt("idnew_table"),
                        resultSet.getString("UsuarioPaciente"),
                        resultSet.getString("NomeUsuario"),
                        resultSet.getString("Data")));
                ConsultasTableView.setItems(mostrandoConsultasObservableList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(verConsultarMedicoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void carregarAcao(){
        //add cell of button edit
        Callback<TableColumn<ConsultaMedicoModelo, String>, TableCell<ConsultaMedicoModelo, String>> cellFoctory = (TableColumn<ConsultaMedicoModelo, String> param) -> {
            // make cell containing buttons
            final TableCell<ConsultaMedicoModelo, String> cell = new TableCell<ConsultaMedicoModelo, String>() {
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
                            try {
                                Usuario user = new Usuario(getUserMed());
                                HelloApplication.Loginsusuario.add(user);
                                ListaID user2 = new ListaID(UsuarioMed.getId());
                                HelloApplication.ListaID.add(user2);
                                Stage stage = (Stage) irConsulta.getScene().getWindow();
                                stage.close();
                                Parent root = FXMLLoader.load(getClass().getResource("ConsultaMed-view.fxml"));
                                Stage AlterarStage = new Stage();
                                AlterarStage.initStyle(StageStyle.UNDECORATED);
                                AlterarStage.setScene(new Scene(root, 600, 400));
                                AlterarStage.show();
                            } catch (Exception e) {
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
        Callback<TableColumn<ConsultaMedicoModelo, String>, TableCell<ConsultaMedicoModelo, String>> cellFoctory = (TableColumn<ConsultaMedicoModelo, String> param) -> {
            // make cell containing buttons
            final TableCell<ConsultaMedicoModelo, String> cell = new TableCell<ConsultaMedicoModelo, String>() {
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
                            try {
                                UsuarioMed = ConsultasTableView.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM new_agendamento WHERE idnew_table=" + UsuarioMed.getId();
                                preparedStatement = connectDB2.prepareStatement(query);
                                preparedStatement.execute();
                                resetaTabela();
                            } catch (SQLException e){
                                Logger.getLogger(verConsultarMedicoController.class.getName()).log(Level.SEVERE, null, e);
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

    public String getNomeMed() {
        return NomeMed;
    }

    public void setNomeMed(String nomeMed) {
        NomeMed = nomeMed;
    }

    public String getUserMed() {
        return UserMed;
    }

    public void setUserMed(String userMed) {
        UserMed = userMed;
    }
}
