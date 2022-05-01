package com.learning.urlshortener.database.links;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.urlshortener.database.links.LinkEntity;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {
}
