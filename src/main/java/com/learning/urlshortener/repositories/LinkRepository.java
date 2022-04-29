package com.learning.urlshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.urlshortener.entities.LinkEntity;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {
}
