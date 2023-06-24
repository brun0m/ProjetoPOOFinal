package com.example.teste123;

import java.sql.Connection;
import java.sql.DriverManager;
public class DataBaseConexao {
    public Connection DataBaseLink;

    public Connection getConnection(){
        String databaseNome = "conta";
        String databaseUsuario = "root";
        String databaseSenha = "123654";

        String url = "jdbc:mysql://localhost:3306/" + databaseNome;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            DataBaseLink = DriverManager.getConnection(url, databaseUsuario, databaseSenha);

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return DataBaseLink;
    }
}
