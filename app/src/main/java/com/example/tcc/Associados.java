package com.example.tcc;

public class Associados {
    private String nome, fone, rg, email, cpf, nasc, end;

    public Associados(){

    }

    public Associados(String nome, String fone, String rg, String email, String cpf, String nasc, String end){
        this.nome = nome;
        this.fone = fone;
        this.rg = rg;
        this.email = email;
        this.cpf = cpf;
        this.nasc = nasc;
        this.end = end;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNasc() {
        return nasc;
    }

    public void setNasc(String nasc) {
        this.nasc = nasc;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
