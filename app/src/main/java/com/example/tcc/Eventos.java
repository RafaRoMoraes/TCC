package com.example.tcc;

public class Eventos {
    private String nomeA, nome, data;

    public Eventos(){

    }

    public Eventos(String nomeA, String nome, String data){
        this.nomeA = nomeA;
        this.nome = nome;
        this.data = data;
    }

    public String getNomeA() {
        return nomeA;
    }

    public void setNomeA(String nomeA) {
        this.nomeA = nomeA;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}