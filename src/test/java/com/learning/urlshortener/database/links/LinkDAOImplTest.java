package com.learning.urlshortener.database.links;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.learning.urlshortener.database.BaseDBTest;
import com.learning.urlshortener.database.SimpleTestObjectFactory;
import com.learning.urlshortener.database.customers.CustomerDAO;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

class LinkDAOImplTest extends BaseDBTest {

    @Autowired
    SimpleTestObjectFactory objectFactory;

    @Autowired
    CustomerDAO customerDAO;

    @Autowired
    LinkDAO underTest;

    Customer existingCustomer;
    Link existingNotSavedLink;

    @BeforeEach
    public void setUp() {
        existingCustomer = customerDAO.saveCustomer(objectFactory.getSimpleCustomer());
        existingNotSavedLink = objectFactory.getSimpleLink();
    }

    @Test
    void should_find_link_by_id() {
        //given
        Long existingLinkId = underTest.saveLink(existingCustomer, existingNotSavedLink).getId();

        //when
        Link foundLink = underTest.findLinkById(existingLinkId);

        //then
        existingNotSavedLink.setId(foundLink.getId());
        assertThat(foundLink).isNotNull().isEqualTo(existingNotSavedLink);
    }

    @Test
    void should_find_link_by_shortenedUrl() {
        //given
        existingNotSavedLink.setShortenedUrl("http://surl.com/test");
        underTest.saveLink(existingCustomer, existingNotSavedLink);

        //when
        Link foundLink = underTest.findLinkByShortenedUrl("http://surl.com/test");

        //then
        existingNotSavedLink.setId(foundLink.getId());
        assertThat(foundLink).isNotNull().isEqualTo(existingNotSavedLink);
    }

    @Test
    void should_find_all_links_for_specific_customer() {
        //given
        underTest.saveLink(existingCustomer, objectFactory.getSimpleLink());
        underTest.saveLink(existingCustomer, objectFactory.getSimpleLink());

        //when
        List<Link> foundLinks = underTest.findAllLinksByCustomer(existingCustomer);

        //then
        assertThat(foundLinks).isNotNull();
        assertThat(foundLinks.size()).isEqualTo(2);
    }

    @Test
    void should_save_new_link() {
        //when
        Long savedLinkId = underTest.saveLink(existingCustomer, existingNotSavedLink).getId();

        //then
        Link derivedFromDatabaseLink = underTest.findLinkById(savedLinkId);
        existingNotSavedLink.setId(savedLinkId);

        assertThat(derivedFromDatabaseLink).isNotNull().isEqualTo(existingNotSavedLink);
        assertThat(underTest.findAllLinksByCustomer(existingCustomer)).hasSize(1).contains(existingNotSavedLink);
    }

    @Test
    void should_delete_link_by_id() {
        //given
        Long existingLinkId = underTest.saveLink(existingCustomer, existingNotSavedLink).getId();

        assertThat(underTest.findAllLinksByCustomer(existingCustomer)).hasSize(1);  // link are saved to db

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

        existingNotSavedLink.setShortenedUrl("http://repeated.url");
        underTest.saveLink(existingCustomer, existingNotSavedLink);

        Link linkToSave = objectFactory.getSimpleLink();
        linkToSave.setShortenedUrl("http://repeated.url");

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .describedAs("Unique index or primary key violation")
                .isThrownBy(() -> underTest.saveLink(existingCustomer, linkToSave));
    }

    @Test
    void when_saving_with_null_shortenedUrl_should_throw_exception() {

        existingNotSavedLink.setShortenedUrl(null);

        assertThatExceptionOfType(ConstraintViolationException.class)
                .describedAs("must not be null")
                .isThrownBy(() -> underTest.saveLink(existingCustomer, existingNotSavedLink));
    }

    @Test
    void when_saving_with_null_url_should_throw_exception() {

        existingNotSavedLink.setUrl(null);

        assertThatExceptionOfType(ConstraintViolationException.class)
                .describedAs("must not be null")
                .isThrownBy(() -> underTest.saveLink(existingCustomer, existingNotSavedLink));
    }
}