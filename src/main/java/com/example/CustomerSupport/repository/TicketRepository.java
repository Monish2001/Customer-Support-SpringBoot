package com.example.CustomerSupport.repository;

import com.example.CustomerSupport.constants.DBConstants;
import com.example.CustomerSupport.entity.Customer;
import com.example.CustomerSupport.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAllByCustomerId(Integer customerId);
    List<Ticket> findAllByStatus(DBConstants.TicketStatus status);
    List<Ticket> findAllByCustomerIdAndStatus(Integer customerId, DBConstants.TicketStatus status);
    List<Ticket> findAllByAgentId(Integer agentId);
}
