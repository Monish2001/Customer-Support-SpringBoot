package com.example.CustomerSupport.repository;

import com.example.CustomerSupport.constants.DBConstants;
import com.example.CustomerSupport.entity.Customer;
import com.example.CustomerSupport.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAllByCustomerId(Integer customerId);
    List<Ticket> findAllByStatus(DBConstants.TicketStatus status);
    List<Ticket> findAllByCustomerIdAndStatus(Integer customerId, DBConstants.TicketStatus status);
    List<Ticket> findAllByAgentId(Integer agentId);
    @Query(value = "SELECT * FROM tickets WHERE agent_id = ?1 AND status_updated_at > ?2 AND status = 'INITIATED' OR status = 'ACTIVE'", nativeQuery = true)
    List<Ticket> findAllByAgentIdAndStatusUpdatedAtGreaterThan(Integer agentId, Timestamp timestamp);
}
