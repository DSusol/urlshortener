package com.learning.urlshortener.database.links;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.urlshortener.database.customers.CustomerEntity;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {

    LinkEntity findLinkEntityByShortenedUrl(String shortenedUrl);

    List<LinkEntity> findLinkEntitiesByCustomer(CustomerEntity customer);
}
