package com.example.CustomerSupport.controller;
import com.example.CustomerSupport.constants.DBConstants;
import com.example.CustomerSupport.entity.Ticket;
import com.example.CustomerSupport.helper.DateHelper;
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
        return ticketService.saveTicket(ticket);
    }

    @GetMapping("/tickets")
    public List<Ticket> fetchTicket(@RequestParam(value="customer_id",required = false) Integer customerId,@RequestParam(value="status",required = false) DBConstants.TicketStatus status)
    {
        if(customerId!=null && status == null)
        {
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
        return ticketService.updateTicket(
                ticket);
    }


}
