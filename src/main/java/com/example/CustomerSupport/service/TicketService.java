package com.example.CustomerSupport.service;
import com.example.CustomerSupport.constants.DBConstants;
import com.example.CustomerSupport.constants.SchedulerConstants;
import com.example.CustomerSupport.entity.Agent;
import com.example.CustomerSupport.entity.Ticket;
import com.example.CustomerSupport.helper.DateHelper;
import com.example.CustomerSupport.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private AgentService agentService;
    public Ticket createTicket(Ticket ticket)
    {
        if(ticket.getAgentId()==null)
        {
            Integer agentId = getAvailableAgent(); //rename to get available agent
            ticket.setAgentId(agentId);
        }
        ticket.setCreatedAt(DateHelper.getCurrentTimeStamp());
        ticket.setUpdatedAt(DateHelper.getCurrentTimeStamp());
        ticket.setStatusUpdatedAt(DateHelper.getCurrentTimeStamp());
        return ticketRepository.save(ticket);
    }
    public List<Ticket> fetchTicketListByCustomerIdAndStatus(Integer customerId, DBConstants.TicketStatus status)
    {
        if(customerId!=null && status == null)
        {
            return ticketRepository.findAllByCustomerId(customerId);
        } else if (status!=null && customerId == null) {
            return ticketRepository.findAllByStatus(status);
        } else if (customerId!=null && status!=null) {
            return ticketRepository.findAllByCustomerIdAndStatus(customerId,status);
        } else{
            return ticketRepository.findAll();
        }
    }
    public List<Ticket> fetchTicketListByAgentId(Integer agentId)
    {
        return ticketRepository.findAllByAgentId(agentId);
    }

    public List<Ticket> fetchTicketListByAgentIdAndDate(Integer agentId)
    {
        Calendar day = Calendar.getInstance();
        day.set(Calendar.MILLISECOND, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.HOUR_OF_DAY, 0);
        Timestamp time = new Timestamp(day.getTimeInMillis());
        return ticketRepository.findAllByAgentIdAndStatusUpdatedAtGreaterThan(agentId,time);
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
        Ticket dbTicket = findById(ticket.getId());
        if(ticket.getStatus()!=null && (!(ticket.getStatus().equals(dbTicket.getStatus()))))
        {
            ticket.setStatusUpdatedAt(DateHelper.getCurrentTimeStamp());
        }
        if(ticket.getCustomerId()==null){
            ticket.setCustomerId(dbTicket.getCustomerId());
        }
        if(ticket.getDescription()==null){
            ticket.setDescription(dbTicket.getDescription());
        }
        if(ticket.getTitle()==null){
            ticket.setTitle(dbTicket.getTitle());
        }
        if(ticket.getCreatedAt()==null){
            ticket.setCreatedAt(dbTicket.getCreatedAt());
        }
        if(ticket.getAgentId()==null){
            ticket.setAgentId(dbTicket.getAgentId());
        }
        if(ticket.getStatus()==null){
            ticket.setStatus(dbTicket.getStatus());
        }
        if(ticket.getStatusUpdatedAt()==null)
        {
            ticket.setStatusUpdatedAt(dbTicket.getStatusUpdatedAt());
        }

        ticket.setUpdatedAt(DateHelper.getCurrentTimeStamp());
        ticketRepository.save(ticket);
        return ticket;
    }

    public Integer getAvailableAgent()
    {
        Map<Integer,Integer> agentTicketCountMap=new HashMap<Integer,Integer>();
        List<Agent> agentList = agentService.fetchAgentList();
        for (Agent agent:
             agentList) {
            List<Ticket> agentTicketList = fetchTicketListByAgentIdAndDate(agent.getId());
            if(agentTicketList.size()==0)
            {
                return agent.getId();
            }
            agentTicketCountMap.put(agent.getId(),agentTicketList.size());
        }
        int minimum_till_now = 999999999;
        int minimum_key_till_now = 999999999;
        for (Map.Entry<Integer,Integer> entry : agentTicketCountMap.entrySet()) {
            if(entry.getValue() < minimum_till_now){
                minimum_till_now = entry.getValue();
                minimum_key_till_now = entry.getKey();
            }
        }
        return minimum_key_till_now;
    }
    @Scheduled(fixedRate = SchedulerConstants.FIXED_RATE)
    public void resolveTicket() {
        List<Ticket> ticketList = fetchTicketListByCustomerIdAndStatus(null,null);
        for (Ticket ticket:
             ticketList) {
            if((ticket.getStatus().equals(DBConstants.TicketStatus.CLOSED)) && ((DateHelper.getCurrentTimeStamp().getTime()- ticket.getStatusUpdatedAt().getTime()) >= MILLISECONDS.convert(2, MINUTES)))
            {
                ticket.setStatus(DBConstants.TicketStatus.RESOLVED);
                updateTicket(ticket);
            }
        }
    }
}
