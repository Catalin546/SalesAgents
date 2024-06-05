package com.example.salesagents.Repository;

import com.example.salesagents.Domain.Agent;


public interface AgentRepository extends Repository<Agent,Long> {
    Agent getByUsernameAndPassword(String username, String password);
}
