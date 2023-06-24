package com.example.teste123;

public class Medico {
    protected String nome;
    protected String usuario;
    protected String especialidade;
    protected boolean camed;
    protected boolean bradesco;
    protected boolean unimed;
    protected boolean amil;
    protected double estrelas;
    protected int numeroavaliacoes;

    public Medico(String nome, String especialidade, String usuario, boolean camed, boolean bradesco, boolean unimed,
                  boolean amil, double estrelas){
        this.nome = nome;
        this.especialidade = especialidade;
        this.usuario = usuario;
        this.camed = camed;
        this.bradesco = bradesco;
        this.unimed = unimed;
        this.amil = amil;
        this.estrelas = estrelas;
    }

    public int getNumeroavaliacoes() {
        return numeroavaliacoes;
    }

    public void setNumeroavaliacoes(int numeroavaliacoes) {
        this.numeroavaliacoes = numeroavaliacoes;
    }

    public double getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(double estrelas) {
        this.estrelas = estrelas;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isUnimed() {
        return unimed;
    }

    public void setUnimed(boolean unimed) {
        this.unimed = unimed;
    }

    public boolean isCamed() {
        return camed;
    }

    public void setCamed(boolean camed) {
        this.camed = camed;
    }

    public boolean isBradesco() {
        return bradesco;
    }

    public void setBradesco(boolean bradesco) {
        this.bradesco = bradesco;
    }

    public boolean isAmil() {
        return amil;
    }

    public void setAmil(boolean amil) {
        this.amil = amil;
    }
}
