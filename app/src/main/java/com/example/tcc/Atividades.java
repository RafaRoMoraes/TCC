package com.example.tcc;

public class Atividades {
    private String profA, nomeA, dataA, descA, dias;

    public Atividades(){

    }

    public Atividades(String nomea, String profa, String dataa, String dias, String desc){
        this.profA = profa;
        this.nomeA = nomea;
        this.dataA = dataa;
        this.descA = desc;
        this.dias = dias;
    }

    public String getDescA() {
        return descA;
    }

    public void setDescA(String descA) {
        this.descA = descA;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getNomeA() {
        return nomeA;
    }

    public void setNomeA(String nomeA) {
        this.nomeA = nomeA;
    }

    public String getProfA() {
        return profA;
    }

    public void setProfA(String profA) {
        this.profA = profA;
    }

    public String getDataA() {
        return dataA;
    }

    public void setDataA(String dataA) {
        this.dataA = dataA;
    }
}
