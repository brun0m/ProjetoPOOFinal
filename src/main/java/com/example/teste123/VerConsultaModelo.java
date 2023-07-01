package com.example.teste123;

public class VerConsultaModelo {
    public int ID;
    public String usuario;
    public String nome;
    public String especialidade;
    public String data;

    public VerConsultaModelo(int ID, String usuario, String nome, String especialidade, String data){
        this.ID = ID;
        this.usuario = usuario;
        this.nome = nome;
        this.especialidade = especialidade;
        this.data = data;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
