package com.example.teste123;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {

    public static Stage stage;
    private static Scene InicioScene;
    private static Scene LoginScene;
    private static Scene CadUsuarioScene;
    private static Scene CadMedicoScene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        FXMLLoader fxmlInicio = new FXMLLoader(HelloApplication.class.getResource("inicio-view.fxml"));
        InicioScene = new Scene(fxmlInicio.load(), 220,360);

        FXMLLoader fxmlLogin = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        LoginScene = new Scene(fxmlLogin.load(), 640, 360);

        Usuario user = new Usuario("?????");
        HelloApplication.Loginsusuario.add(user);
        HelloApplication.Armazen.add(user);

        FXMLLoader fxmlCadastroUsuario = new FXMLLoader(HelloApplication.class.getResource("cadastrousuario-view.fxml"));
        CadUsuarioScene = new Scene(fxmlCadastroUsuario.load(),600,400);

        FXMLLoader fxmlCadastroMedico = new FXMLLoader(HelloApplication.class.getResource("cadastromedico-view.fxml"));
        CadMedicoScene = new Scene(fxmlCadastroMedico.load(),600,400);

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
            case 3:
                stage.setTitle("Cadastro Usuário");
                stage.setScene(CadUsuarioScene);
                break;
            case 6:
                stage.setTitle("Cadastro Médico");
                stage.setScene(CadMedicoScene);
                break;
        }
    }

    public static ArrayList<Usuario> Loginsusuario = new ArrayList<>();
    public static ArrayList<Usuario> Armazen = new ArrayList<>();
    public static ArrayList<ListaID> ListaID = new ArrayList<>();
    public static void main(String[] args) {
        launch();
    }
}