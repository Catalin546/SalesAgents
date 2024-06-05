package com.example.salesagents.Service;

import com.example.salesagents.Domain.*;
import java.util.List;


public interface Service  {
     List<Produs> getAllProduse();

     Comanda addComanda(Agent agent, Produs produs, int cantitate);

     Agent login(String username, String password) throws Exception;

     Agent addAgent(Agent agent);

     Agent updateAgent(Agent agent);

     Agent deleteAgent(Long id);

     List<Agent> getAllAgents(Agent agent);

     IObservable getObservable();
}
