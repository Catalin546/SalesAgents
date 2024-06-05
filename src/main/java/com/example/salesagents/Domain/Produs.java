package com.example.salesagents.Domain;


public class Produs extends Entity<Long> {
    private String nume;

    private Double pret;

    private int cantitate;

    private String descriere;

    public Produs(Long id,String nume, Double pret, int cantitate,String descriere) {
        super(id);
        this.nume = nume;
        this.pret = pret;
        this.cantitate = cantitate;
        this.descriere = descriere;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Double getPret() {
        return pret;
    }

    public void setPret(Double  pret) {
        this.pret = pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }
}
