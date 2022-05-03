package com.learning.urlshortener.database.customers;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomerFinder {

    private final CustomerRepository customerRepository;

    public CustomerEntity findCustomerEntityById(Long id) {
        //todo: implement exception handling
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer was not found"));
    }
}
