package com.learning.urlshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.urlshortener.entities.CustomerEntity;

public interface UserRepository extends JpaRepository<CustomerEntity, Long> {
}
