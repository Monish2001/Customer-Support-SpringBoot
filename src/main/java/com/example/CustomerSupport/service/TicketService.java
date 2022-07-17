package com.example.CustomerSupport.service;
import com.example.CustomerSupport.entity.Ticket;
import com.example.CustomerSupport.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    public Ticket saveTicket(Ticket ticket)
    {
        Date date = new Date();
        Timestamp timestamp2 = new Timestamp(date.getTime());
        ticket.setCreatedAt(timestamp2);
        ticket.setUpdatedAt(timestamp2);
        return ticketRepository.save(ticket);
    }
    public List<Ticket> fetchTicketList()
    {
        return (List<Ticket>)
                ticketRepository.findAll();
    }
    public List<Ticket> fetchTicketListByCustomerId(Integer customerId)
    {
        return (List<Ticket>)
                ticketRepository.findAllByCustomerId(customerId);
    }
    public List<Ticket> fetchTicketListByStatus(String status)
    {
        return (List<Ticket>)
                ticketRepository.findAllByStatus(status);
    }
    public List<Ticket> fetchTicketListByCustomerIdAndStatus(Integer customerId, String status)
    {
        return (List<Ticket>)
                ticketRepository.findAllByCustomerIdAndStatus(customerId,status);
    }
    public Ticket findById(Integer id)
    {
        return ticketRepository.findById(id).get();
    }
    public void deleteTicketById(Integer ticketId)
    {
        ticketRepository.deleteById(ticketId);
    }
    public Ticket updateTicket(Ticket ticket)
    {
        ticketRepository.save(ticket);
        return ticket;
    }
}
