package com.learning.urlshortener.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;
import com.learning.urlshortener.entities.CustomerEntity;
import com.learning.urlshortener.mappers.CustomerMapper;
import com.learning.urlshortener.mappers.LinkMapper;
import com.learning.urlshortener.repositories.CustomerRepository;
import com.learning.urlshortener.repositories.LinkRepository;

class LinkDaoServiceImplTest extends BaseDBTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    LinkMapper linkMapper;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    LinkDaoService serviceUnderTest;

    @Test
    void should_find_link_by_shortenedUrl() {
        //given
        Link existingLink = Link.builder()
                .shortenedUrl("http://surl.com/test")
                .url("http://example.com/long_url")
                .clickCount(5)
                .build();
        entityManager.persist(linkMapper.linkToLinkEntity(existingLink));

        //when
        Link foundLink = serviceUnderTest.findLinkByShortenedUrl("http://surl.com/test");

        //then
        assertNotNull(foundLink);
        assertThat(foundLink.getShortenedUrl()).isEqualTo("http://surl.com/test");
        assertThat(foundLink.getUrl()).isEqualTo("http://example.com/long_url");
        assertThat(foundLink.getClickCount()).isEqualTo(5);
    }

    @Test
    void should_save_new_link() {
        //given
        Customer customer = Customer.builder().nickname("test nickname").build();
        CustomerEntity customerEntity = customerMapper.customerToCustomerEntity(customer);
        entityManager.persist(customerEntity);

        Link linkToSave = Link.builder()
                .shortenedUrl("http://surl.com/test")
                .url("http://example.com/long_url")
                .clickCount(5)
                .build();

        //when
        Link savedLink = serviceUnderTest.saveLink(customer, linkToSave);

        //then
        assertNotNull(savedLink);
        assertThat(savedLink.getShortenedUrl()).isEqualTo("http://surl.com/test");
        assertThat(savedLink.getUrl()).isEqualTo("http://example.com/long_url");
        assertThat(savedLink.getClickCount()).isEqualTo(5);
        assertThat(linkRepository.count()).isEqualTo(1);
    }

    @Test
    void should_delete_link() {
        //given
        Link existingLink = Link.builder()
                .shortenedUrl("http://surl.com/test")
                .url("http://example.com/long_url")
                .clickCount(5)
                .build();
        entityManager.persist(linkMapper.linkToLinkEntity(existingLink));

        //when
        serviceUnderTest.deleteLink(existingLink);

        //then
        assertThat(linkRepository.count()).isEqualTo(0);
    }
}