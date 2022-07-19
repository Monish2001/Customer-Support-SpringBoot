package com.example.CustomerSupport.controller;
import com.example.CustomerSupport.constants.DBConstants;
import com.example.CustomerSupport.converter.TicketConverter;
import com.example.CustomerSupport.dto.TicketDTO;
import com.example.CustomerSupport.entity.Ticket;
import com.example.CustomerSupport.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/tickets")
    public Ticket saveTicket(
            @RequestBody TicketDTO ticketDTO)
    {
        Ticket ticket = new TicketConverter().ticketDtoToTicket(ticketDTO);
        return ticketService.create(ticket);
    }

    @GetMapping("/tickets")
    public List<Ticket> fetchTicket(@RequestParam(value="customer_id",required = false) Integer customerId,@RequestParam(value="status",required = false) DBConstants.TicketStatus status)
    {
//        System.out.println(customerId);
//        System.out.println(status);
        return ticketService.getTickets(customerId,status);
    }

    @GetMapping("/tickets/{id}")
    public Ticket findById(@PathVariable("id") Integer ticketId)
    {
        return ticketService.getTicket(ticketId);
    }

    @DeleteMapping("/tickets/{id}")
    public void deleteTicketById(@PathVariable("id")
                                   Integer ticketId)
    {
        ticketService.delete(
                ticketId);
    }

    @PutMapping("/tickets")
    public Ticket updateTicket(@RequestBody TicketDTO ticketDTO)
    {
        Ticket ticket = new TicketConverter().ticketDtoToTicket(ticketDTO);
        return ticketService.update(
                ticket);
    }
}
