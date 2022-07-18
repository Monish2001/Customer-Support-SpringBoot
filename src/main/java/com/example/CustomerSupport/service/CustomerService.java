package com.example.CustomerSupport.service;

import com.example.CustomerSupport.entity.Customer;
import com.example.CustomerSupport.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;


    public Customer createCustomer(Customer customer)
    {
        return customerRepository.save(customer);
    }
    public List<Customer> fetchCustomerList()
    {
        return customerRepository.findAll();
    }
    public Customer findById(Integer id)
    {
        return customerRepository.findById(id).get();
    }
    public void deleteCustomerById(Integer customerId)
    {
        customerRepository.deleteById(customerId);
    }
    public Customer updateCustomer(Customer customer)
    {
        customerRepository.save(customer);
        return customer;
    }
}
