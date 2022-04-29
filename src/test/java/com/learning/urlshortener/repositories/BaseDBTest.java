package com.learning.urlshortener.repositories;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest
@ComponentScan("com.learning.urlshortener.repositories")
public class BaseDBTest {
}
