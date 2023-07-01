package com.example.teste123;

public class ConsultaMedicoModelo {
    public int id;
    public String nome,usuario,data;

    public ConsultaMedicoModelo(int id, String nome, String usuario, String data) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
