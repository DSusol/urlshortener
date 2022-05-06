package com.learning.urlshortener.database.customers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.learning.urlshortener.database.BaseDBTest;
import com.learning.urlshortener.database.SimpleTestObjectFactory;
import com.learning.urlshortener.database.links.LinkDAO;
import com.learning.urlshortener.domain.Customer;

class CustomerDAOImplTest extends BaseDBTest {

    @Autowired
    LinkDAO linkDAO;

    @Autowired
    CustomerDAO underTest;

    @BeforeEach
    void setUp() {
        linkDAO.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void should_find_customer_by_id() {
        //given
        Customer existingCustomer = Customer.builder().nickname("test nickname").build();
        Long existingCustomerId = underTest.saveCustomer(existingCustomer).getId();

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
        assertThat(underTest.findCustomerById(savedCustomer.getId()).getNickname()).isEqualTo("test nickname");
    }

    @Test
    void should_delete_customer_by_id() {
        //given
        Long existingCustomerId = underTest.saveCustomer(SimpleTestObjectFactory.getSimpleCustomer()).getId();
        assertThat(underTest.findCustomerById(existingCustomerId)).isNotNull();  // check if customer exists in db

        //when
        underTest.deleteCustomerById(existingCustomerId);

        //then
        assertThatExceptionOfType(EntityNotFoundException.class)
                .describedAs("Customer was not found")
                .isThrownBy(() -> underTest.findCustomerById(existingCustomerId));
    }

    @Test
    void should_delete_customer_and_related_links() {
        //given
        Customer existingCustomer = underTest.saveCustomer(SimpleTestObjectFactory.getSimpleCustomer());

        Long linkId = linkDAO.saveLink(existingCustomer, SimpleTestObjectFactory.getSimpleLink()).getId();

        assertThat(underTest.findCustomerById(existingCustomer.getId())).isNotNull();      // customer is saved to db
        assertThat(linkDAO.findAllLinksByCustomer(existingCustomer)).hasSize(1);  // link are saved to db

        //when
        underTest.deleteCustomerById(existingCustomer.getId());

        //then
        assertThatExceptionOfType(EntityNotFoundException.class)
                .describedAs("Customer was not found")
                .isThrownBy(() -> underTest.findCustomerById(existingCustomer.getId()));

        assertThat(linkDAO.findLinkById(linkId)).isEmpty();
    }

    @Test
    void when_saving_with_existing_nickname_should_throw_exception() {

        underTest.saveCustomer(Customer.builder().nickname("test nickname").build());
        Customer customerToSave = Customer.builder().nickname("test nickname").build();

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .describedAs("Unique index or primary key violation")
                .isThrownBy(() -> underTest.saveCustomer(customerToSave));
    }

    @Test
    void when_saving_with_null_nickname_should_throw_exception() {
        Customer customerToSave = SimpleTestObjectFactory.getSimpleCustomer();
        customerToSave.setNickname(null);

        assertThatExceptionOfType(ConstraintViolationException.class)
                .describedAs("must not be null")
                .isThrownBy(() -> underTest.saveCustomer(customerToSave));
    }
}