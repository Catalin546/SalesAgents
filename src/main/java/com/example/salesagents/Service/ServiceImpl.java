package com.example.salesagents.Service;

import com.example.salesagents.Domain.Agent;
import com.example.salesagents.Domain.Comanda;
import com.example.salesagents.Domain.IObservable;
import com.example.salesagents.Domain.Produs;
import com.example.salesagents.Repository.AgentRepository;
import com.example.salesagents.Repository.ComandaRepository;
import com.example.salesagents.Repository.ProdusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ServiceImpl implements Service {
    private AgentRepository agentRepository;
    private ProdusRepository produsRepository;
    private ComandaRepository comandaRepository;

    private IObservable observable = new IObservable();

    public ServiceImpl(AgentRepository agentRepository, ProdusRepository produsRepository, ComandaRepository comandaRepository) {
        this.agentRepository = agentRepository;
        this.produsRepository = produsRepository;
        this.comandaRepository = comandaRepository;
    }

    @Override
    public List<Produs> getAllProduse() {
        return produsRepository.getAll();
    }

    @Override
    public Comanda addComanda(Agent agent, Produs produs, int cantitate) {
        Comanda comanda = new Comanda(null, agent, produs, cantitate);
        if (comandaRepository.save(comanda).isPresent()) {
            observable.notifyAllObservers(null);
            return comanda;
        } else {
            return null;
        }
    }

    @Override
    public Agent addAgent(Agent agent) {
        if (agent.getUsername() == null || agent.getPassword() == null) {
            return null;
        }
        List<Agent> agents = agentRepository.getAll();
        for (Agent a : agents) {
            if (a.getPassword().equals(agent.getPassword())) {
                return null;
            }
        }
        agentRepository.save(agent);
        return agent;
    }

    @Override
    public Agent updateAgent(Agent agent) {
        if(agent.getPassword() == null || agent.getUsername() == null){
            return null;
        }
        List<Agent> agents = agentRepository.getAll();
        for (Agent a : agents) {
            if (a.getPassword().equals(agent.getPassword())) {
                return null;
            }
        }
        agentRepository.update(agent);
        return agent;
    }

    @Override
    public Agent deleteAgent(Long id) {
        Optional<Agent> agent = agentRepository.getById(id);
        if (agent != null) {
            agentRepository.deleteById(id);
            return agent.orElse(null);
        }
    return null;}

    @Override
    public List<Agent> getAllAgents(Agent agent) {
        List<Agent> agents = agentRepository.getAll();
        List<Agent> agentsWithoutActualAgent = new ArrayList<>();
        for ( Agent a : agents){
            if (a.getUsername() != agent.getUsername() && a.getPassword() != agent.getPassword()){
                agentsWithoutActualAgent.add(a);
            }
        }
        return agentsWithoutActualAgent;
    }

    @Override
    public IObservable getObservable() {
        return observable;
    }

    @Override
    public Agent login(String username, String password) throws Exception {
        Agent agent = agentRepository.getByUsernameAndPassword(username, password);
        if(agent!=null){
            return agent;
    }
        return null;
    }
}
