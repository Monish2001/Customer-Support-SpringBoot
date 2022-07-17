package com.example.CustomerSupport.service;

import com.example.CustomerSupport.entity.Agent;
import com.example.CustomerSupport.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentService {
    @Autowired
    private AgentRepository agentRepository;
    public Agent saveAgent(Agent agent)
    {
        return agentRepository.save(agent);
    }
    public List<Agent> fetchAgentList()
    {
        return (List<Agent>)
                agentRepository.findAll();
    }
    public void deleteAgentById(Integer agentId)
    {
        agentRepository.deleteById(agentId);
    }
    public Agent updateAgent(Agent agent)
    {
        agentRepository.save(agent);
        return agent;
    }

}
