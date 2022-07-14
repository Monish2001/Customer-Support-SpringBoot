package com.example.CustomerSupport.repository;

import com.example.CustomerSupport.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Annotation
@Repository

// Interface
public interface AgentRepository
        extends JpaRepository<Agent, Integer> {
}