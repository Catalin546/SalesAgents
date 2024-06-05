package com.example.salesagents.Domain;


public class Comanda extends Entity<Long> {
    private Produs produs;

    private Agent agent;

    private int cantitate;

    public Comanda(Long id,Agent agent, Produs produs, int cantitate) {
        super(id);
        this.agent = agent;
        this.produs = produs;
        this.cantitate = cantitate;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Produs getProdus() {
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }
}

