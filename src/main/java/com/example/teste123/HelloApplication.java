package com.example.teste123;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Stage stage;
    private static Scene InicioScene;
    private static Scene LoginScene;
    private static Scene MenuScene;
    private static Scene CadUsuarioScene;
    private static Scene AgendarConsultaScene;
    private static Scene ClinicoGeralScene;
    private static Scene CadMedicoScene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        FXMLLoader fxmlInicio = new FXMLLoader(HelloApplication.class.getResource("inicio-view.fxml"));
        InicioScene = new Scene(fxmlInicio.load(), 220,360);

        FXMLLoader fxmlLogin = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        LoginScene = new Scene(fxmlLogin.load(), 640, 360);

        FXMLLoader fxmlMenu = new FXMLLoader(HelloApplication.class.getResource("menu-view.fxml"));
        MenuScene = new Scene(fxmlMenu.load(), 640,360);

        FXMLLoader fxmlAgendar = new FXMLLoader(HelloApplication.class.getResource("agendarconsulta-view.fxml"));
        AgendarConsultaScene = new Scene(fxmlAgendar.load(), 800, 600);

        FXMLLoader fxmlCadastroUsuario = new FXMLLoader(HelloApplication.class.getResource("cadastrousuario-view.fxml"));
        CadUsuarioScene = new Scene(fxmlCadastroUsuario.load(),600,400);

        FXMLLoader fxmlCadastroMedico = new FXMLLoader(HelloApplication.class.getResource("cadastromedico-view.fxml"));
        CadMedicoScene = new Scene(fxmlCadastroMedico.load(),600,400);

        FXMLLoader fxmlClinicoGeral = new FXMLLoader(HelloApplication.class.getResource("clinicageral-view.fxml"));
        ClinicoGeralScene = new Scene(fxmlClinicoGeral.load(),600,400);

        primaryStage.setTitle("Inicio");
        primaryStage.setScene(InicioScene);
        primaryStage.show();
    }

    public static void mudarTela(int a){
        switch(a){
            case 0:
                stage.setTitle("Inicio");
                stage.setScene(InicioScene);
                break;
            case 1:
                stage.setTitle("Login");
                stage.setScene(LoginScene);
                break;
            case 2:
                stage.setTitle("Menu");
                stage.setScene(MenuScene);
                break;
            case 3:
                stage.setTitle("Cadastro Usuário");
                stage.setScene(CadUsuarioScene);
                break;
            case 4:
                stage.setTitle("Agendar Consulta");
                stage.setScene(AgendarConsultaScene);
                break;
            case 5:
                stage.setTitle("Clínico Geral");
                stage.setScene(ClinicoGeralScene);
                break;
            case 6:
                stage.setTitle("Cadastro Médico");
                stage.setScene(CadMedicoScene);
                break;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}