package com.learning.urlshortener.services;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest
@ComponentScan("com.learning.urlshortener.mappers")
@ComponentScan("com.learning.urlshortener.repositories")
@ComponentScan("com.learning.urlshortener.services")
public class BaseDBTest {
}
