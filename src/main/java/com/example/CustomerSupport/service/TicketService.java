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
    public Ticket saveTicket(Ticket ticket)
    {
        if(ticket.getAgentId()==null)
        {
            Integer agentId = assignAgentToTicket(); //rename to get available agent
            ticket.setAgentId(agentId);
        }
        ticket.setCreatedAt(DateHelper.getCurrentTimeStamp());
        ticket.setUpdatedAt(DateHelper.getCurrentTimeStamp());
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
        ticket.setCreatedAt(dbTicket.getCreatedAt());
        ticket.setUpdatedAt(DateHelper.getCurrentTimeStamp());
        ticketRepository.save(ticket);
        return ticket;
    }

    public Integer assignAgentToTicket()
    {
        Map<Integer,Integer> agentTicketCountMap=new HashMap<Integer,Integer>();
        List<Agent> agentList = agentService.fetchAgentList();
        for (Agent agent:
             agentList) {
            List<Ticket> agentTicketList = fetchTicketListByAgentId(agent.getId());
            if(agentTicketList.size()==0)
            {
                return agent.getId();
            }
            int agentTicketCount = 0;
            for (Ticket ticket:
                 agentTicketList) {
                if(ticket.getStatus().equals(DBConstants.TicketStatus.INITIATED) || ticket.getStatus().equals(DBConstants.TicketStatus.ACTIVE))
                {
                    if(DateHelper.isSameDay(new Date(DateHelper.getCurrentTimeStamp().getTime()),new Date(ticket.getUpdatedAt().getTime())))
                    {
                        agentTicketCount+=1;
                    }
                }
            }
            agentTicketCountMap.put(agent.getId(),agentTicketCount);
        }
        LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();
        agentTicketCountMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        Map.Entry<Integer,Integer> entry = sortedMap.entrySet().iterator().next();
        return entry.getKey();
    }
    @Scheduled(fixedRate = SchedulerConstants.FIXED_RATE)
    public void resolveTicket() {
        List<Ticket> ticketList = fetchTicketListByCustomerIdAndStatus(null,null);
        if(ticketList.size()!=0)
        {
            for (Ticket ticket:
                 ticketList) {
                if((ticket.getStatus().equals(DBConstants.TicketStatus.CLOSED)) && ((DateHelper.getCurrentTimeStamp().getTime()- ticket.getUpdatedAt().getTime()) >= MILLISECONDS.convert(5, MINUTES)))
                {
                    ticket.setStatus(DBConstants.TicketStatus.RESOLVED);
                    updateTicket(ticket);
                }
            }
        }
    }
}
