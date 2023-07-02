package com.example.teste123;

public class HistoricoModelo {
    String Nome;
    int Estrelas;
    String Comentario;

    public HistoricoModelo(String nome, int estrelas, String comentario) {
        Nome = nome;
        Estrelas = estrelas;
        Comentario = comentario;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public int getEstrelas() {
        return Estrelas;
    }

    public void setEstrelas(int estrelas) {
        Estrelas = estrelas;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }
}
