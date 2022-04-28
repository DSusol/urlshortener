package com.learning.urlshortener.repositories;

import com.learning.urlshortener.entities.CustomerEntity;
import com.learning.urlshortener.entities.LinkEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest extends BaseKabanDBTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    UserRepository repositoryUnderTest;

    @Test
    void should_find_no_customer_if_repository_is_empty() {
        List<CustomerEntity> customerList = repositoryUnderTest.findAll();

        assertEquals(0, repositoryUnderTest.count());
        assertTrue(customerList.isEmpty());
    }

    @Test
    void should_find_customer_by_id() {
        //given
        entityManager.persist(CustomerEntity.builder().nickname("Nickname1").build());
        CustomerEntity savedCustomer = entityManager.persist(CustomerEntity.builder().nickname("Nickname2").build());
        entityManager.persist(CustomerEntity.builder().nickname("Nickname3").build());

        //when
        CustomerEntity foundCustomer = repositoryUnderTest.findById(savedCustomer.getId()).orElse(null);

        //then
        assertNotNull(foundCustomer);
        assertEquals(savedCustomer, foundCustomer);
    }

    @Test
    void should_find_all_customers() {
        entityManager.persist(new CustomerEntity());
        entityManager.persist(new CustomerEntity());
        entityManager.persist(new CustomerEntity());

        assertEquals(3, repositoryUnderTest.count());
    }

    @Test
    void should_save_new_customer() {
        //given
        CustomerEntity newCustomer = CustomerEntity.builder().nickname("Nickname").build();

        //when
        CustomerEntity savedCustomer = repositoryUnderTest.save(newCustomer);

        //then
        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());
        assertEquals("Nickname", savedCustomer.getNickname());
    }

    @Test
    void should_save_all_customers() {
        //given
        CustomerEntity customer1 = CustomerEntity.builder().nickname("Nickname1").build();
        CustomerEntity customer2 = CustomerEntity.builder().nickname("Nickname2").build();

        //when
        List<CustomerEntity> savedCustomerList = repositoryUnderTest.saveAll(List.of(customer1, customer2));

        //then
        assertNotNull(savedCustomerList);
        assertEquals(2, savedCustomerList.size());
    }

    @Test
    void should_delete_customer_by_id() {
        //given
        entityManager.persist(CustomerEntity.builder().nickname("Nickname1").build());
        CustomerEntity customerToDelete = entityManager.persist(CustomerEntity.builder().nickname("Nickname2").build());
        entityManager.persist(CustomerEntity.builder().nickname("Nickname3").build());

        //when
        repositoryUnderTest.deleteById(customerToDelete.getId());

        //then
        assertEquals(2, repositoryUnderTest.count());
        assertNull(repositoryUnderTest.findById(customerToDelete.getId()).orElse(null));
    }

    @Test
    void should_delete_all_customers() {
        //given
        entityManager.persist(CustomerEntity.builder().nickname("Nickname1").build());
        entityManager.persist(CustomerEntity.builder().nickname("Nickname2").build());
        entityManager.persist(CustomerEntity.builder().nickname("Nickname3").build());

        //when
        repositoryUnderTest.deleteAll();

        //then
        assertEquals(0, repositoryUnderTest.count());
    }

    @Test
    void should_delete_customer_and_related_links() {
        //given
        CustomerEntity customer = entityManager.persist(new CustomerEntity());

        LinkEntity link1 = LinkEntity.builder().customer(customer).build();
        LinkEntity link2 = LinkEntity.builder().customer(customer).build();
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