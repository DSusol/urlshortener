package com.learning.urlshortener.database.customers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.learning.urlshortener.database.BaseDBTest;
import com.learning.urlshortener.database.links.LinkDAO;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

class CustomerDAOImplTest extends BaseDBTest {

    @Autowired
    LinkDAO linkDAO;

    @Autowired
    CustomerDAO underTest;

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
    void should_update_existing_customer() {
        //given
        Customer existingCustomer = Customer.builder().nickname("test nickname").build();
        Long existingCustomerId = underTest.saveCustomer(existingCustomer).getId();

        Customer customerToUpdate = Customer.builder().id(existingCustomerId).nickname("updated nickname").build();

        //when
        Customer updatedCustomer = underTest.updateCustomer(customerToUpdate);

        //then
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer).isEqualTo(customerToUpdate);
        assertThat(underTest.findCustomerById(existingCustomerId).getNickname()).isEqualTo("updated nickname");
    }

    @Test
    void should_delete_customer_by_id() {
        //given
        Long existingCustomerId = underTest.saveCustomer(new Customer()).getId();

        //when
        underTest.deleteCustomerById(existingCustomerId);

        //then
        assertThatExceptionOfType(RuntimeException.class)
                .describedAs("Customer was not found")
                .isThrownBy(
                        () -> underTest.findCustomerById(existingCustomerId));
    }

    @Test
    void should_delete_customer_and_related_links() {
        //given
        Customer existingCustomer = underTest.saveCustomer(new Customer());

        Long link1Id = linkDAO.saveLink(existingCustomer, new Link()).getId();
        Long link2Id = linkDAO.saveLink(existingCustomer, new Link()).getId();

        assertThat(underTest.findCustomerById(existingCustomer.getId())).isNotNull();      // customer is saved to db
        assertThat(linkDAO.findAllLinksByCustomer(existingCustomer)).hasSize(2);  // link are saved to db

        //when
        underTest.deleteCustomerById(existingCustomer.getId());

        //then
        assertThatExceptionOfType(RuntimeException.class)
                .describedAs("Customer was not found")
                .isThrownBy(() -> underTest.findCustomerById(existingCustomer.getId()));

        assertThatExceptionOfType(RuntimeException.class)
                .describedAs("Link was not found")
                .isThrownBy(() -> linkDAO.findLinkById(link1Id));

        assertThatExceptionOfType(RuntimeException.class)
                .describedAs("Link was not found")
                .isThrownBy(() -> linkDAO.findLinkById(link2Id));
    }

    @Test
    void when_saving_with_existing_nickname_should_throw_exception() {

        underTest.saveCustomer(Customer.builder().nickname("test nickname").build());
        Customer customerToSave = Customer.builder().nickname("test nickname").build();

        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(
                () -> underTest.saveCustomer(customerToSave));
    }
}