package com.learning.urlshortener.database.links;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import com.learning.urlshortener.database.BaseDBTest;
import com.learning.urlshortener.database.customers.CustomerEntity;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

class LinkDAOImplTest extends BaseDBTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    LinkDAO underTest;

    @Test
    void should_find_link_by_id() {
        //given
        LinkEntity existingLinkEntity = LinkEntity.builder()
                .shortenedUrl("http://surl.com/test")
                .url("http://example.com/long_url")
                .clickCount(5)
                .build();
        Long existingLinkEntityId = entityManager.persist(existingLinkEntity).getId();

        //when
        Link foundLink = underTest.findLinkById(existingLinkEntityId);

        //then
        assertThat(foundLink)
                .isNotNull()
                .extracting("shortenedUrl", "url", "clickCount")
                .contains("http://surl.com/test", "http://example.com/long_url", 5);
    }

    @Test
    void should_save_new_link() {
        //given
        CustomerEntity existingCustomerEntity = entityManager.persist(new CustomerEntity());
        Customer inputCustomer = Customer.builder().id(existingCustomerEntity.getId()).build();

        Link linkToSave = Link.builder()
                .shortenedUrl("http://surl.com/test")
                .url("http://example.com/long_url")
                .clickCount(5)
                .build();

        //when
        Link savedLink = underTest.saveLink(inputCustomer, linkToSave);

        //then
        LinkEntity savedLinkEntity = entityManager.find(LinkEntity.class, savedLink.getId());
        CustomerEntity savedCustomerEntity = entityManager.find(CustomerEntity.class, existingCustomerEntity.getId());

        assertThat(savedLink)
                .isNotNull()
                .extracting("shortenedUrl", "url", "clickCount")
                .contains("http://surl.com/test", "http://example.com/long_url", 5);


        assertThat(savedLinkEntity)
                .isNotNull()
                .extracting("shortenedUrl", "url", "clickCount")
                .contains("http://surl.com/test", "http://example.com/long_url", 5);

        assertThat(savedCustomerEntity.getLinks())
                .hasSize(1)
                .contains(savedLinkEntity);
    }

    @Test
    void should_delete_link_by_id() {
        //given
        CustomerEntity existingCustomerEntity = entityManager.persist(new CustomerEntity());
        LinkEntity existingLinkEntity = entityManager.persist(
                LinkEntity.builder().customer(existingCustomerEntity).build()
        );
        existingCustomerEntity.getLinks().add(existingLinkEntity);
        Long existingLinkEntityId = existingLinkEntity.getId();

        //when
        underTest.deleteLinkById(existingLinkEntityId);

        //then
        assertThat(entityManager.find(LinkEntity.class, existingLinkEntityId)).isNull();

        CustomerEntity savedCustomerEntity = entityManager.find(CustomerEntity.class, existingCustomerEntity.getId());
        assertThat(savedCustomerEntity.getLinks()).isEmpty();
    }

    @Test
    void when_saving_with_existing_shortenedUrl_should_throw_exception() {

        LinkEntity existingLinkEntity = LinkEntity.builder().shortenedUrl("http://url.com/").build();
        entityManager.persist(existingLinkEntity);

        Link linkToSave = Link.builder().shortenedUrl("http://url.com/").build();

        CustomerEntity existingCustomerEntity = entityManager.persist(new CustomerEntity());
        Customer inputCustomer = Customer.builder().id(existingCustomerEntity.getId()).build();

        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(
                () -> underTest.saveLink(inputCustomer, linkToSave));
    }

    @Test
    void when_saving_link_with_invalid_url_should_throw_exception() {

        LinkEntity existingLinkEntity = LinkEntity.builder().url("invalid_url").build();

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(
                () -> entityManager.persist(existingLinkEntity));
    }

    @Test
    void when_saving_link_with_invalid_shortenedUrl_should_throw_exception() {

        LinkEntity existingLinkEntity = LinkEntity.builder().shortenedUrl("invalid_url").build();

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(
                () -> entityManager.persist(existingLinkEntity));
    }

    @Test
    void when_saving_link_with_invalid_clickCount_property_should_throw_exception() {

        LinkEntity existingLinkEntity = LinkEntity.builder().clickCount(-2).build();

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(
                () -> entityManager.persist(existingLinkEntity));
    }
}