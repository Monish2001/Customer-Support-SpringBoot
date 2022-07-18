package com.example.CustomerSupport.service;

import com.example.CustomerSupport.entity.Agent;
import com.example.CustomerSupport.entity.Ticket;
import com.example.CustomerSupport.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentService {
    @Autowired
    private AgentRepository agentRepository;
    public Agent createAgent(Agent agent)
    {
        return agentRepository.save(agent);
    }
    public List<Agent> fetchAgentList()
    {
        return agentRepository.findAll();
    }
    public Agent findById(Integer id)
    {
        return agentRepository.findById(id).get();
    }
    public void deleteAgentById(Integer agentId)
    {
        agentRepository.deleteById(agentId);
    }
    public Agent updateAgent(Agent agent)
    {
        Agent dbAgent = findById(agent.getId());
        if(agent.getEmail()==null){
            agent.setEmail(dbAgent.getEmail());
        }
        if(agent.getName()==null){
            agent.setName(dbAgent.getName());
        }
        agentRepository.save(agent);
        return agent;
    }

}
