package com.example.teste123;

public class ConsultaMedicoModelo {
    public int ID;
    public String nome, usuario, data;

    public ConsultaMedicoModelo(int ID, String nome, String usuario, String data) {
        this.ID = ID;
        this.nome = nome;
        this.usuario = usuario;
        this.data = data;
    }

    public int getId() {
        return ID;
    }

    public void setId(int ID) {
        this.ID = ID;
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
