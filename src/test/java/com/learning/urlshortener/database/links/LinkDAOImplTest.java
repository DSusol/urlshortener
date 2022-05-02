package com.learning.urlshortener.database.links;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.learning.urlshortener.database.BaseDBTest;
import com.learning.urlshortener.database.customers.CustomerDAO;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

class LinkDAOImplTest extends BaseDBTest {

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    LinkDAO underTest;

    @Test
    void should_find_link_by_id() {
        //given
        Customer existingCustomer = customerDAO.saveCustomer(new Customer());
        Link existingLink = Link.builder()
                .shortenedUrl("http://surl.com/test")
                .url("http://example.com/long_url")
                .clickCount(5)
                .build();
        Long existingLinkId = underTest.saveLink(existingCustomer, existingLink).getId();

        //when
        Link foundLink = underTest.findLinkById(existingLinkId);

        //then
        assertThat(foundLink)
                .isNotNull()
                .extracting("shortenedUrl", "url", "clickCount")
                .contains("http://surl.com/test", "http://example.com/long_url", 5);
    }

    @Test
    void should_find_link_by_shortenedUrl() {
        //given
        Customer existingCustomer = customerDAO.saveCustomer(new Customer());
        Link existingLink = Link.builder()
                .shortenedUrl("http://surl.com/test")
                .url("http://example.com/long_url")
                .clickCount(5)
                .build();
        underTest.saveLink(existingCustomer, existingLink);

        //when
        Link foundLink = underTest.findLinkByShortenedUrl("http://surl.com/test");

        //then
        assertThat(foundLink)
                .isNotNull()
                .extracting("shortenedUrl", "url", "clickCount")
                .contains("http://surl.com/test", "http://example.com/long_url", 5);
    }

    @Test
    void should_find_all_links_for_specific_customer() {
        //given
        Customer existingCustomer = customerDAO.saveCustomer(new Customer());

        underTest.saveLink(existingCustomer, new Link());
        underTest.saveLink(existingCustomer, new Link());

        //when
        List<Link> foundLinks = underTest.findAllLinksByCustomer(existingCustomer);

        //then
        assertThat(foundLinks).isNotNull();
        assertThat(foundLinks.size()).isEqualTo(2);
    }

    @Test
    void should_save_new_link() {
        //given
        Customer existingCustomer = customerDAO.saveCustomer(new Customer());
        Link linkToSave = Link.builder()
                .shortenedUrl("http://surl.com/test")
                .url("http://example.com/long_url")
                .clickCount(5)
                .build();

        //when
        Link savedLink = underTest.saveLink(existingCustomer, linkToSave);

        //then
        Link derivedFromDatabaseLink = underTest.findLinkById(savedLink.getId());

        assertThat(underTest.findAllLinksByCustomer(existingCustomer))
                .hasSize(1)
                .contains(derivedFromDatabaseLink);

        assertThat(derivedFromDatabaseLink)
                .isNotNull()
                .extracting("shortenedUrl", "url", "clickCount")
                .contains("http://surl.com/test", "http://example.com/long_url", 5);
    }

    @Test
    void should_delete_link_by_id() {
        //given
        Customer existingCustomer = customerDAO.saveCustomer(new Customer());
        Long existingLinkId = underTest.saveLink(existingCustomer, new Link()).getId();

        //when
        underTest.deleteLinkById(existingLinkId);

        //then
        assertThat(underTest.findAllLinksByCustomer(existingCustomer)).isEmpty();
        assertThatExceptionOfType(RuntimeException.class)
                .describedAs("Link was not found")
                .isThrownBy(() -> underTest.findLinkById(existingLinkId));
    }

    @Test
    void when_saving_with_existing_shortenedUrl_should_throw_exception() {

        Customer existingCustomer = customerDAO.saveCustomer(new Customer());
        underTest.saveLink(existingCustomer, Link.builder().shortenedUrl("http://url.com/").build());

        Link linkToSave = Link.builder().shortenedUrl("http://url.com/").build();

        assertThatExceptionOfType(DataIntegrityViolationException.class).isThrownBy(
                () -> underTest.saveLink(existingCustomer, linkToSave));
    }

    @Test
    void when_saving_link_with_invalid_url_should_throw_exception() {

        Customer existingCustomer = customerDAO.saveCustomer(new Customer());
        Link linkToSave = Link.builder().url("invalid_url").build();

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(
                () -> underTest.saveLink(existingCustomer, linkToSave));
    }

    @Test
    void when_saving_link_with_invalid_shortenedUrl_should_throw_exception() {

        Customer existingCustomer = customerDAO.saveCustomer(new Customer());
        Link linkToSave = Link.builder().shortenedUrl("invalid_url").build();

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(
                () -> underTest.saveLink(existingCustomer, linkToSave));
    }

    @Test
    void when_saving_link_with_invalid_clickCount_property_should_throw_exception() {

        Customer existingCustomer = customerDAO.saveCustomer(new Customer());
        Link linkToSave = Link.builder().clickCount(-2).build();

        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(
                () -> underTest.saveLink(existingCustomer, linkToSave));
    }
}