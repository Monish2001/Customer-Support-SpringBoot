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
        return agentService.saveAgent(agent);
    }

    @GetMapping("/agents")
    public List<Agent> fetchAgentList()
    {
        return agentService.fetchAgentList();
    }

    @DeleteMapping("/agents/{id}")
    public void deleteAgentById(@PathVariable("id")
                                       Integer agentId)
    {
        agentService.deleteAgentById(
                agentId);
//        return "Deleted Successfully";
    }

//    @PutMapping("/agents/{id}")
    @PutMapping("/agents")

//    public Agent updateAgent(@RequestBody Agent agent,
//                     @PathVariable("id") Integer agentId)
    public Agent updateAgent(@RequestBody Agent agent)
    {
//        return agentService.updateAgent(
//                agent, agentId);
        return agentService.updateAgent(
                agent);
    }
}
