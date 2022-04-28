package com.learning.urlshortener.repositories;

import com.learning.urlshortener.entities.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {
}
