package com.learning.urlshortener.database;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest
@ComponentScan("com.learning.urlshortener.database")
public class BaseDBTest {
}
