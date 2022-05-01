package com.learning.urlshortener.database.customers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.urlshortener.database.customers.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
