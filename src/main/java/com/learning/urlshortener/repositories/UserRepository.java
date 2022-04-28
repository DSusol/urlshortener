package com.learning.urlshortener.repositories;

import com.learning.urlshortener.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<CustomerEntity, Long> {
}
