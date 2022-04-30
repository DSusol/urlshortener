package com.learning.urlshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.urlshortener.entities.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    CustomerEntity findCustomerEntityByNickname(String nickname);
}
