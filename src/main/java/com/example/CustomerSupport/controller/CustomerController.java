package com.example.CustomerSupport.controller;

import com.example.CustomerSupport.entity.Agent;
import com.example.CustomerSupport.entity.Customer;
import com.example.CustomerSupport.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/customers")
    public Customer saveCustomer(
            @RequestBody Customer customer)
    {
        return customerService.saveCustomer(customer);
    }

    @GetMapping("/customers")
    public List<Customer> fetchCustomerList()
    {
        return customerService.fetchCustomerList();
    }

    @GetMapping("/customers/{id}")
    public Customer findById(@PathVariable("id") Integer customerId)
    {
        return customerService.findById(customerId);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomerById(@PathVariable("id")
                                Integer customerId)
    {
        customerService.deleteCustomerById(
                customerId);
//        return "Deleted Successfully";
    }

    @PutMapping("/customers")

    public Customer updateCustomer(@RequestBody Customer customer)
    {
        return customerService.updateCustomer(
                customer);
    }
}
