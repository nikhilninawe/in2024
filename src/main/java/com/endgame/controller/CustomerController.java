package com.endgame.controller;

import javax.annotation.PostConstruct;

import com.endgame.dto.Customer;
import com.endgame.repository.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class CustomerController {

    final CustomerRepository repository;
	public CustomerController(@Autowired CustomerRepository customerRepository) {
        this.repository = customerRepository;
    }

    @GetMapping("/customer")
    public Customer getCustomer() {
        return repository.findByFirstName("Alice");
    }
}