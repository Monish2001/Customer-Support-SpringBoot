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
    public Ticket create(Ticket ticket)
    {
        Agent agent = new Agent();
        if(ticket.getAgent()==null)
        {
            Integer agentId = getAvailableAgent(); //rename to get available agent
            agent.setId(agentId);
            ticket.setAgent(agent);
        }
        ticket.setCreatedAt(DateHelper.getCurrentTimeStamp());
        ticket.setUpdatedAt(DateHelper.getCurrentTimeStamp());
        ticket.setStatusUpdatedAt(DateHelper.getCurrentTimeStamp());
        return ticketRepository.save(ticket);
    }
    public List<Ticket> getTickets(Integer customerId, DBConstants.TicketStatus status)
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
//        return ticketRepository.findAllByCustomerIdAndStatus(customerId,status);
    }
    public List<Ticket> getTicketsByAgentId(Integer agentId)
    {
        return ticketRepository.findAllByAgentId(agentId);
    }

    private List<Ticket> getTicketListByAgentIdAndDate(Integer agentId)
    {
        Calendar day = Calendar.getInstance();
        day.set(Calendar.MILLISECOND, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.HOUR_OF_DAY, 0);
        Timestamp time = new Timestamp(day.getTimeInMillis());
        List<DBConstants.TicketStatus> statusList = new ArrayList<DBConstants.TicketStatus>();
        statusList.add(DBConstants.TicketStatus.ACTIVE);
        statusList.add(DBConstants.TicketStatus.INITIATED);
        return ticketRepository.findAllByAgentIdAndStatusInAndStatusUpdatedAtGreaterThan(agentId,statusList,time);
    }

    public Ticket getTicket(Integer id)
    {
        return ticketRepository.findById(id).get();
    }
    public void delete(Integer ticketId)
    {
        ticketRepository.deleteById(ticketId);
    }
    public Ticket update(Ticket ticket)
    {
        Ticket dbTicket = getTicket(ticket.getId());
        if(ticket.getStatus()!=null && (!(ticket.getStatus().equals(dbTicket.getStatus()))))
        {
            ticket.setStatusUpdatedAt(DateHelper.getCurrentTimeStamp());
        }
        if(ticket.getCustomer()==null){
            ticket.setCustomer(dbTicket.getCustomer());
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
        if(ticket.getAgent()==null){
            ticket.setAgent(dbTicket.getAgent());
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

    private Integer getAvailableAgent()
    {
        Map<Integer,Integer> agentTicketCountMap=new HashMap<Integer,Integer>();
        List<Agent> agentList = agentService.getAgents();
        for (Agent agent:
             agentList) {
            List<Ticket> agentTicketList = getTicketListByAgentIdAndDate(agent.getId());
            if(agentTicketList.size()==0)
            {
                return agent.getId();
            }
            agentTicketCountMap.put(agent.getId(),agentTicketList.size());
        }
        Integer key = Collections.min(agentTicketCountMap.entrySet(), Map.Entry.comparingByValue()).getKey();
        return key;
    }
    @Scheduled(fixedRate = SchedulerConstants.FIXED_RATE)
    private void resolveTicket() {
        List<Ticket> ticketList = getTickets(null,null);
        for (Ticket ticket:
             ticketList) {
            if((ticket.getStatus().equals(DBConstants.TicketStatus.CLOSED)) && ((DateHelper.getCurrentTimeStamp().getTime()- ticket.getStatusUpdatedAt().getTime()) >= MILLISECONDS.convert(2, MINUTES)))
            {
                ticket.setStatus(DBConstants.TicketStatus.RESOLVED);
                update(ticket);
            }
        }
    }
}
