package com.learning.urlshortener.repositories;

import com.learning.urlshortener.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
