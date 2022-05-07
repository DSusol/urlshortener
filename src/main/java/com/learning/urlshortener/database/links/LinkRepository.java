package com.learning.urlshortener.database.links;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.urlshortener.database.customers.CustomerEntity;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {

    Optional<LinkEntity> findLinkEntityByShortenedUrl(String shortenedUrl);

    List<LinkEntity> findLinkEntitiesByCustomer(CustomerEntity customer);
}
