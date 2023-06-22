package com.example.teste123;

public class Usuario {
    protected String nome;
    protected String usuario;
    protected int CPF;
    protected String plano;
    protected String senha;

    public Usuario(String nome, String usuario, int CPF, String plano, String senha){
        this.nome = nome;
        this.usuario = usuario;
        this.CPF = CPF;
        this.plano = plano;
        this.senha = senha;
    }

    public int getCPF() {
        return CPF;
    }

    public void setCPF(int CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
