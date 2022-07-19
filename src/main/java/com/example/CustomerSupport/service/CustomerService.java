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


    public Customer create(Customer customer)
    {
        return customerRepository.save(customer);
    }
    public List<Customer> getCustomers()
    {
        return customerRepository.findAll();
    }
    public Customer getCustomer(Integer id)
    {
        return customerRepository.findById(id).get();
    }
    public void delete(Integer customerId)
    {
        customerRepository.deleteById(customerId);
    }
    public Customer update(Customer customer)
    {
        Customer dbCustomer = getCustomer(customer.getId());
        if(customer.getEmail()==null){
            customer.setEmail(dbCustomer.getEmail());
        }
        if(customer.getName()==null){
            customer.setName(dbCustomer.getName());
        }
        customerRepository.save(customer);
        return customer;
    }
}
