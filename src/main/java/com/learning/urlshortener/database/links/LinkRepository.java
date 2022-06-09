package com.learning.urlshortener.database.links;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.urlshortener.database.customers.CustomerEntity;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {

    Optional<LinkEntity> findLinkEntityByToken(String token);

    List<LinkEntity> findLinkEntitiesByCustomerAndUrl(CustomerEntity customer, String url);

    List<LinkEntity> findLinkEntitiesByCustomer(CustomerEntity customer);
}
