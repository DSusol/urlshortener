package com.learning.urlshortener.database.links;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeAll;
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
    CustomerDAO customerDAO;

    @Autowired
    LinkDAO underTest;

    Customer existingCustomer;
    Link linkToSave;

    @BeforeEach
    public void setUp() {
        existingCustomer = customerDAO.saveCustomer(SimpleTestObjectFactory.getSimpleCustomer());
        linkToSave = SimpleTestObjectFactory.getSimpleLink();
    }

    @Test
    void should_find_link_by_id() {
        //given
        Long existingLinkId = underTest.saveLink(existingCustomer, linkToSave).getId();

        //when
        Link foundLink = underTest.findLinkById(existingLinkId).orElse(null);

        //then
        assertThat(foundLink).isNotNull();

        linkToSave.setId(foundLink.getId());
        assertThat(foundLink).isEqualTo(linkToSave);
    }

    @Test
    void when_find_by_incorrect_id_should_return_empty_result() {
        assertThat(underTest.findLinkById(5L)).isEmpty();
    }

    @Test
    void should_find_link_by_shortenedUrl() {
        //given
        linkToSave.setShortenedUrl("http://surl.com/test");
        underTest.saveLink(existingCustomer, linkToSave);

        //when
        Link foundLink = underTest.findLinkByShortenedUrl("http://surl.com/test").orElse(null);

        //then
        assertThat(foundLink).isNotNull();

        linkToSave.setId(foundLink.getId());
        assertThat(foundLink).isEqualTo(linkToSave);
    }

    @Test
    void when_find_by_incorrect_shortenedUrl_should_return_empty_result() {
        assertThat(underTest.findLinkByShortenedUrl("incorrect dada")).isEmpty();
    }

    @Test
    void should_find_all_links_for_specific_customer() {
        //given
        underTest.saveLink(existingCustomer, SimpleTestObjectFactory.getSimpleLink());
        underTest.saveLink(existingCustomer, SimpleTestObjectFactory.getSimpleLink());

        //when
        List<Link> foundLinks = underTest.findAllLinksByCustomer(existingCustomer);

        //then
        assertThat(foundLinks).isNotNull();
        assertThat(foundLinks.size()).isEqualTo(2);
    }

    @Test
    void should_save_new_link() {
        //when
        Long savedLinkId = underTest.saveLink(existingCustomer, linkToSave).getId();

        //then
        Link derivedFromDatabaseLink = underTest.findLinkById(savedLinkId).orElse(null);
        linkToSave.setId(savedLinkId);

        assertThat(derivedFromDatabaseLink).isNotNull().isEqualTo(linkToSave);
        assertThat(underTest.findAllLinksByCustomer(existingCustomer)).hasSize(1).contains(linkToSave);
    }

    @Test
    void should_delete_link_by_id() {
        //given
        Long existingLinkId = underTest.saveLink(existingCustomer, linkToSave).getId();

        assertThat(underTest.findAllLinksByCustomer(existingCustomer)).hasSize(1);  // link are saved to db

        //when
        underTest.deleteLinkById(existingLinkId);

        //then
        assertThat(underTest.findLinkById(existingLinkId)).isEmpty();
        assertThat(underTest.findAllLinksByCustomer(existingCustomer)).isEmpty();

    }

    @Test
    void when_saving_with_existing_shortenedUrl_should_throw_exception() {

        linkToSave.setShortenedUrl("http://repeated.url");
        underTest.saveLink(existingCustomer, linkToSave);

        Link linkToSave = SimpleTestObjectFactory.getSimpleLink();
        linkToSave.setShortenedUrl("http://repeated.url");

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .describedAs("Unique index or primary key violation")
                .isThrownBy(() -> underTest.saveLink(existingCustomer, linkToSave));
    }

    @Test
    void when_saving_with_null_shortenedUrl_should_throw_exception() {

        linkToSave.setShortenedUrl(null);

        assertThatExceptionOfType(ConstraintViolationException.class)
                .describedAs("must not be null")
                .isThrownBy(() -> underTest.saveLink(existingCustomer, linkToSave));
    }

    @Test
    void when_saving_with_null_url_should_throw_exception() {

        linkToSave.setUrl(null);

        assertThatExceptionOfType(ConstraintViolationException.class)
                .describedAs("must not be null")
                .isThrownBy(() -> underTest.saveLink(existingCustomer, linkToSave));
    }
}