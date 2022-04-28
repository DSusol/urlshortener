package com.learning.urlshortener.repositories;

import com.learning.urlshortener.domains.Customer;
import com.learning.urlshortener.domains.Link;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    UserRepository repositoryUnderTest;

    @Test
    void should_find_no_customer_if_repository_is_empty() {
        List<Customer> customerList = repositoryUnderTest.findAll();

        assertEquals(0, repositoryUnderTest.count());
        assertTrue(customerList.isEmpty());
    }

    @Test
    void should_find_customer_by_id() {
        //given
        entityManager.persist(Customer.builder().nickname("Nickname1").build());
        Customer savedCustomer = entityManager.persist(Customer.builder().nickname("Nickname2").build());
        entityManager.persist(Customer.builder().nickname("Nickname3").build());

        //when
        Customer foundCustomer = repositoryUnderTest.findById(savedCustomer.getId()).orElse(null);

        //then
        assertNotNull(foundCustomer);
        assertEquals(savedCustomer, foundCustomer);
    }

    @Test
    void should_find_all_customers() {
        entityManager.persist(new Customer());
        entityManager.persist(new Customer());
        entityManager.persist(new Customer());

        assertEquals(3, repositoryUnderTest.count());
    }

    @Test
    void should_save_new_customer() {
        //given
        Customer newCustomer = Customer.builder().nickname("Nickname").build();

        //when
        Customer savedCustomer = repositoryUnderTest.save(newCustomer);

        //then
        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());
        assertEquals("Nickname", savedCustomer.getNickname());
    }

    @Test
    void should_save_all_customers() {
        //given
        Customer customer1 = Customer.builder().nickname("Nickname1").build();
        Customer customer2 = Customer.builder().nickname("Nickname2").build();

        //when
        List<Customer> savedCustomerList = repositoryUnderTest.saveAll(List.of(customer1, customer2));

        //then
        assertNotNull(savedCustomerList);
        assertEquals(2, savedCustomerList.size());
    }

    @Test
    void should_delete_customer_by_id() {
        //given
        entityManager.persist(Customer.builder().nickname("Nickname1").build());
        Customer customerToDelete = entityManager.persist(Customer.builder().nickname("Nickname2").build());
        entityManager.persist(Customer.builder().nickname("Nickname3").build());

        //when
        repositoryUnderTest.deleteById(customerToDelete.getId());

        //then
        assertEquals(2, repositoryUnderTest.count());
        assertNull(repositoryUnderTest.findById(customerToDelete.getId()).orElse(null));
    }

    @Test
    void should_delete_all_customers() {
        //given
        entityManager.persist(Customer.builder().nickname("Nickname1").build());
        entityManager.persist(Customer.builder().nickname("Nickname2").build());
        entityManager.persist(Customer.builder().nickname("Nickname3").build());

        //when
        repositoryUnderTest.deleteAll();

        //then
        assertEquals(0, repositoryUnderTest.count());
    }

    @Test
    void should_delete_customer_and_related_links() {
        //given
        Customer customer = entityManager.persist(new Customer());

        Link link1 = Link.builder().customer(customer).build();
        Link link2 = Link.builder().customer(customer).build();
        linkRepository.saveAll(List.of(link1, link2));

        customer.setLinks(List.of(link1, link2));
        entityManager.persist(customer);


        assertEquals(1, repositoryUnderTest.count());  //verifying customer is saved
        assertEquals(2, linkRepository.count());       //verifying links are saved

        //when
        repositoryUnderTest.deleteById(customer.getId());

        //then
        assertEquals(0, linkRepository.count());
        assertEquals(0, repositoryUnderTest.count());
    }
}