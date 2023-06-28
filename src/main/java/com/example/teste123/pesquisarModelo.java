package com.example.teste123;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class pesquisarModelo {
    String Nome, Especialidade, Avaliacao;
    Double Estrelas;

    public pesquisarModelo(String Nome, String Especialidade, Double Estrelas, String Avaliacao) {
        this.Nome = Nome;
        this.Especialidade = Especialidade;
        this.Avaliacao = Avaliacao;
        this.Estrelas = Estrelas;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEspecialidade() {
        return Especialidade;
    }

    public void setEspecialidade(String especialidade) {
        Especialidade = especialidade;
    }

    public String getAvaliacao() {
        return Avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        Avaliacao = avaliacao;
    }

    public double getEstrelas() {
        return Estrelas;
    }

    public void setEstrelas(Double estrelas) {
        Estrelas = estrelas;
    }
}

