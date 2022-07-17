package com.example.CustomerSupport.controller;
import com.example.CustomerSupport.entity.Ticket;
import com.example.CustomerSupport.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/tickets")
    public Ticket saveTicket(
            @RequestBody Ticket ticket)
    {
        System.out.println(ticket.getAgentId());
        System.out.println(ticket.getCreatedAt());
        System.out.println(ticket.getDescription());
        if(ticket.getAgentId()==null)
        {

        }
        return ticketService.saveTicket(ticket);
    }

    @GetMapping("/tickets")
    public List<Ticket> fetchTicket(@RequestParam(value="customer_id",required = false) Integer customerId,@RequestParam(value="status",required = false) String status)
    {
        if(customerId!=null && status == null)
        {
            System.out.println("inside");
            return ticketService.fetchTicketListByCustomerId(customerId);
        } else if (status!=null && customerId == null) {
            return ticketService.fetchTicketListByStatus(status);
        } else if (customerId!=null && status!=null) {
            return ticketService.fetchTicketListByCustomerIdAndStatus(customerId,status);
        }

        return ticketService.fetchTicketList();
    }

    @GetMapping("/tickets/{id}")
    public Ticket findById(@PathVariable("id") Integer ticketId)
    {
        return ticketService.findById(ticketId);
    }

    @DeleteMapping("/tickets/{id}")
    public void deleteTicketById(@PathVariable("id")
                                   Integer ticketId)
    {
        ticketService.deleteTicketById(
                ticketId);
    }

    @PutMapping("/tickets")
    public Ticket updateTicket(@RequestBody Ticket ticket)
    {
        Ticket dbTicket = ticketService.findById(ticket.getId());
        ticket.setCreatedAt(dbTicket.getCreatedAt());
        Date date = new Date();
        Timestamp timestamp2 = new Timestamp(date.getTime());
        ticket.setUpdatedAt(timestamp2);
        return ticketService.updateTicket(
                ticket);
    }
}
