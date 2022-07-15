package com.example.CustomerSupport.repository;

import com.example.CustomerSupport.entity.Agent;
import com.example.CustomerSupport.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}