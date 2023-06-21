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

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        FXMLLoader fxmlInicio = new FXMLLoader(HelloApplication.class.getResource("inicio-view.fxml"));
        InicioScene = new Scene(fxmlInicio.load(), 640,360);

        FXMLLoader fxmlLogin = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        LoginScene = new Scene(fxmlLogin.load(), 640, 360);

        FXMLLoader fxmlMenu = new FXMLLoader(HelloApplication.class.getResource("menu-view.fxml"));
        MenuScene = new Scene(fxmlMenu.load(), 640,360);

        FXMLLoader fxmlCadastroUsuario = new FXMLLoader(HelloApplication.class.getResource("cadastrousuario-view.fxml"));
        CadUsuarioScene = new Scene(fxmlCadastroUsuario.load(),640,360);
        primaryStage.setTitle("Inicio");
        primaryStage.setScene(InicioScene);
        primaryStage.show();
    }

    public static void mudarTela(int a){
        switch(a){
            case 0:
                stage.setTitle("Inicio");
                stage.setScene(InicioScene);
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
        }
    }

    public static void main(String[] args) {
        launch();
    }
}