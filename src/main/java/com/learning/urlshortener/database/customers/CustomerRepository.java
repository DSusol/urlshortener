package com.learning.urlshortener.database.customers;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    CustomerEntity findCustomerEntitiesByChatId(Long chatId);
}
