package com.learning.urlshortener.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;
import com.learning.urlshortener.entities.CustomerEntity;
import com.learning.urlshortener.entities.LinkEntity;
import com.learning.urlshortener.mappers.CustomerMapper;
import com.learning.urlshortener.mappers.LinkMapper;
import com.learning.urlshortener.repositories.CustomerRepository;
import com.learning.urlshortener.repositories.LinkRepository;

class CustomerDaoServiceImplTest extends BaseDBTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    LinkMapper linkMapper;


    @Autowired
    CustomerDaoService serviceUnderTest;

    @Test
    void should_find_customer_by_nickname() {
        //given
        Customer existingCustomer = Customer.builder().nickname("Test Nickname").build();
        entityManager.persist(customerMapper.customerToCustomerEntity(existingCustomer));

        //when
        Customer foundCustomer = serviceUnderTest.findCustomerByNickname("Test Nickname");

        //then
        assertNotNull(foundCustomer);
        assertThat(foundCustomer).isEqualTo(existingCustomer);
    }

    @Test
    void should_save_new_customer() {
        //given
        Customer customerToSave = Customer.builder().nickname("Test Nickname").build();

        //when
        Customer savedCustomer = serviceUnderTest.saveCustomer(customerToSave);

        //then
        assertNotNull(savedCustomer);
        assertThat(savedCustomer).isEqualTo(customerToSave);
        assertThat(customerRepository.count()).isEqualTo(1);
    }

    @Test
    void should_update_customer_with_new_nickname() {
        //given
        Customer existingCustomer = Customer.builder().nickname("Test Nickname").build();
        entityManager.persist(customerMapper.customerToCustomerEntity(existingCustomer));

        //when
        Customer modifiedCustomer = serviceUnderTest.updateCustomer(existingCustomer, "New Nickname");

        //then
        assertNotNull(modifiedCustomer);
        assertThat(modifiedCustomer.getNickname()).isEqualTo("New Nickname");
        assertThat(customerRepository.count()).isEqualTo(1);
    }

    @Test
    void should_delete_customer() {
        //given
        Customer existingCustomer = Customer.builder().nickname("Test Nickname").build();
        entityManager.persist(customerMapper.customerToCustomerEntity(existingCustomer));

        //when
        serviceUnderTest.deleteCustomer(existingCustomer);

        //then
        assertThat(customerRepository.count()).isEqualTo(0);
    }

    @Test
    void should_find_all_links_for_specific_customer() {
        //given
        Customer customer = Customer.builder().nickname("test nickname").build();
        CustomerEntity customerEntity = customerMapper.customerToCustomerEntity(customer);
        CustomerEntity savedCustomerEntity = entityManager.persist(customerEntity);

        LinkEntity linkEntity1 = linkMapper.linkToLinkEntity(new Link());
        linkEntity1.setCustomer(savedCustomerEntity);
        entityManager.persist(linkEntity1);

        LinkEntity linkEntity2 = linkMapper.linkToLinkEntity(new Link());
        linkEntity2.setCustomer(savedCustomerEntity);
        entityManager.persist(linkEntity2);

        savedCustomerEntity.setLinks(List.of(linkEntity1, linkEntity2));

        //when
        List<Link> foundLinks = serviceUnderTest.findAllLinksByCustomer(customer);

        //then
        assertNotNull(foundLinks);
        assertThat(foundLinks.size()).isEqualTo(2);
    }

    @Test
    void should_delete_customer_and_related_links() {
        //given
        Customer customer = Customer.builder().nickname("test nickname").build();
        CustomerEntity customerEntity = customerMapper.customerToCustomerEntity(customer);
        CustomerEntity savedCustomerEntity = entityManager.persist(customerEntity);

        LinkEntity link1 = LinkEntity.builder()
                .shortenedUrl("http://test.com/1")
                .customer(savedCustomerEntity)
                .build();
        entityManager.persist(link1);
        LinkEntity link2 = LinkEntity.builder()
                .shortenedUrl("http://test.com/2")
                .customer(savedCustomerEntity)
                .build();
        entityManager.persist(link2);

        savedCustomerEntity.setLinks(List.of(link1, link2));

        assertEquals(1, customerRepository.count());   //verifying customer is saved
        assertEquals(2, linkRepository.count());       //verifying links are saved

        //when
        serviceUnderTest.deleteCustomer(customer);

        //then
        assertThat(linkRepository.count()).isEqualTo(0);
        assertThat(customerRepository.count()).isEqualTo(0);
    }
}