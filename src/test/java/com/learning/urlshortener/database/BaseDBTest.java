package com.learning.urlshortener.database;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.learning.urlshortener.TestContainerSupplier;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("com.learning.urlshortener.database")
// CRUD tests execution will not start transaction (to imitate full operation),
// all DAO operations are transactional (default "start-or-join" propagation)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class BaseDBTest extends TestContainerSupplier {
}
