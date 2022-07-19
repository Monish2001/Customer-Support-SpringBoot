package com.example.CustomerSupport.dto;

import com.example.CustomerSupport.constants.DBConstants;
import com.example.CustomerSupport.entity.Agent;
import com.example.CustomerSupport.entity.Customer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
public class TicketDTO {
    private Integer id;
    private String title;
    private String description;
    private DBConstants.TicketStatus status;
    private Integer customerId;
    private Integer agentId;
    private Date createdAt;
    private Date updatedAt;
    private Date statusUpdatedAt;
}
