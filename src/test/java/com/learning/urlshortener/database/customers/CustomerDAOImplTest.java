package com.learning.urlshortener.database.customers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import com.learning.urlshortener.database.BaseDBTest;
import com.learning.urlshortener.database.links.LinkEntity;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

class CustomerDAOImplTest extends BaseDBTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CustomerDAO underTest;

    @Test
    void should_find_customer_by_id() {
        //given
        CustomerEntity existingCustomerEntity = CustomerEntity.builder().nickname("test nickname").build();
        Long existingCustomerId = entityManager.persist(existingCustomerEntity).getId();

        //when
        Customer foundCustomer = underTest.findCustomerById(existingCustomerId);

        //then
        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getNickname()).isEqualTo("test nickname");
    }

    @Test
    void should_save_new_customer() {
        //given
        Customer customerToSave = Customer.builder().nickname("test nickname").build();

        //when
        Customer savedCustomer = underTest.saveCustomer(customerToSave);

        //then
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getNickname()).isEqualTo("test nickname");
        assertThat(entityManager.find(CustomerEntity.class, savedCustomer.getId()).getNickname())
                .isEqualTo("test nickname");
    }

    @Test
    void should_update_existing_customer() {
        //given
        CustomerEntity existingCustomerEntity = CustomerEntity.builder().nickname("test nickname").build();
        Long existingCustomerId = entityManager.persist(existingCustomerEntity).getId();

        Customer customerToUpdate = Customer.builder().id(existingCustomerId).nickname("updated nickname").build();

        //when
        Customer updatedCustomer = underTest.updateCustomer(customerToUpdate);

        //then
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer).isEqualTo(customerToUpdate);
        assertThat(entityManager.find(CustomerEntity.class, existingCustomerId).getNickname())
                .isEqualTo("updated nickname");
    }

    @Test
    void should_delete_customer_by_id() {
        //given
        CustomerEntity existingCustomerEntity = CustomerEntity.builder().nickname("test nickname").build();
        Long existingCustomerId = entityManager.persist(existingCustomerEntity).getId();

        //when
        underTest.deleteCustomerById(existingCustomerId);

        //then
        assertThat(entityManager.find(CustomerEntity.class, existingCustomerId)).isNull();
    }

    @Test
    void should_find_all_links_for_specific_customer() {
        //given
        CustomerEntity customerEntity = CustomerEntity.builder().nickname("test nickname").build();
        CustomerEntity existingCustomerEntity = entityManager.persist(customerEntity);

        LinkEntity linkEntity1 = LinkEntity.builder().clickCount(2).customer(existingCustomerEntity).build();
        entityManager.persist(linkEntity1);
        LinkEntity linkEntity2 = LinkEntity.builder().clickCount(3).customer(existingCustomerEntity).build();
        entityManager.persist(linkEntity2);

        existingCustomerEntity.setLinks(List.of(linkEntity1, linkEntity2));

        Customer customer = Customer.builder().id(existingCustomerEntity.getId()).build();

        //when
        List<Link> foundLinks = underTest.findAllLinksByCustomer(customer);

        //then
        assertThat(foundLinks).isNotNull();
        assertThat(foundLinks.size()).isEqualTo(2);
    }

    @Test
    void should_delete_customer_and_related_links() {
        //given
        CustomerEntity customerEntity = CustomerEntity.builder().nickname("test nickname").build();
        CustomerEntity existingCustomerEntity = entityManager.persist(customerEntity);
        Long existingCustomerId = existingCustomerEntity.getId();

        LinkEntity linkEntity1 = LinkEntity.builder().clickCount(2).customer(existingCustomerEntity).build();
        Long linkEntity1Id = entityManager.persist(linkEntity1).getId();
        LinkEntity linkEntity2 = LinkEntity.builder().clickCount(3).customer(existingCustomerEntity).build();
        Long linkEntity2Id = entityManager.persist(linkEntity2).getId();

        existingCustomerEntity.setLinks(List.of(linkEntity1, linkEntity2));

        assertThat(entityManager.find(CustomerEntity.class, existingCustomerId)).isNotNull(); // customer is in db
        assertThat(entityManager.find(LinkEntity.class, linkEntity1Id)).isNotNull();          // link is saved to db
        assertThat(entityManager.find(LinkEntity.class, linkEntity2Id)).isNotNull();          // link is saved to db

        //when
        underTest.deleteCustomerById(existingCustomerId);

        //then
        assertThat(entityManager.find(CustomerEntity.class, existingCustomerId)).isNull();
        assertThat(entityManager.find(LinkEntity.class, linkEntity1Id)).isNull();
        assertThat(entityManager.find(LinkEntity.class, linkEntity2Id)).isNull();
    }

    @Test
    void when_saving_with_existing_nickname_should_throw_exception() {

        CustomerEntity existingCustomerEntity = CustomerEntity.builder().nickname("test nickname").build();
        entityManager.persist(existingCustomerEntity);

        Customer customerToSave = Customer.builder().nickname("test nickname").build();

        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(
                () -> underTest.saveCustomer(customerToSave));
    }
}