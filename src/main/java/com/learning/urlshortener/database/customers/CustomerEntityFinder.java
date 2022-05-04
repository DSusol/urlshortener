package com.learning.urlshortener.database.customers;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomerEntityFinder {

    private final CustomerRepository customerRepository;

    public CustomerEntity findCustomerEntityById(Long id) {
        //todo: implement exception handling
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer was not found"));
    }
}
