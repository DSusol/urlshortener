package com.learning.urlshortener.repositories;

import com.learning.urlshortener.domains.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Customer, Long> {
}
