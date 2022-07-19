package com.example.CustomerSupport.controller;

import com.example.CustomerSupport.entity.Agent;
import com.example.CustomerSupport.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AgentController {
    @Autowired private AgentService agentService;

    @PostMapping("/agents")
    public Agent saveAgent(
            @RequestBody Agent agent)
    {
        return agentService.create(agent);
    }

    @GetMapping("/agents")
    public List<Agent> fetchAgentList()
    {
        return agentService.getAgents();
    }

    @DeleteMapping("/agents/{id}")
    public void deleteAgentById(@PathVariable("id")
                                       Integer agentId)
    {
        agentService.delete(
                agentId);
    }

    @PutMapping("/agents")
    public Agent updateAgent(@RequestBody Agent agent)
    {
        return agentService.update(
                agent);
    }
}
