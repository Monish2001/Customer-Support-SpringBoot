package com.example.CustomerSupport.converter;

import com.example.CustomerSupport.dto.TicketDTO;
import com.example.CustomerSupport.entity.Agent;
import com.example.CustomerSupport.entity.Customer;
import com.example.CustomerSupport.entity.Ticket;

public class TicketConverter {
    public Ticket ticketDtoToTicket(TicketDTO ticketDTO)
    {
        Ticket ticket = new Ticket();
        if(ticketDTO.getId()!=null){
            ticket.setId(ticketDTO.getId());
        }
        if(ticketDTO.getTitle()!=null)
        {
            ticket.setTitle(ticketDTO.getTitle());
        }
        if(ticketDTO.getDescription()!=null)
        {
            ticket.setDescription(ticketDTO.getDescription());
        }
        if(ticketDTO.getCustomerId()!=null){
            Customer customer = new Customer();
            customer.setId(ticketDTO.getCustomerId());
            ticket.setCustomer(customer);
        }
        if(ticketDTO.getAgentId()!=null){
            Agent agent = new Agent();
            agent.setId(ticketDTO.getAgentId());
            ticket.setAgent(agent);
        }
        if(ticketDTO.getStatus()!=null){
            ticket.setStatus(ticketDTO.getStatus());
        }
        return ticket;
    }
}
