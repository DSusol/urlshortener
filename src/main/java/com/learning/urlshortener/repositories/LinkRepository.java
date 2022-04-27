package com.learning.urlshortener.repositories;

import com.learning.urlshortener.domains.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
