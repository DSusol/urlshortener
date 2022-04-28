package com.learning.urlshortener.repositories;

import com.learning.urlshortener.domains.Customer;
import com.learning.urlshortener.domains.Link;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LinkRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    LinkRepository repositoryUnderTest;

    @Test
    void should_find_no_link_if_repository_is_empty() {
        List<Link> linkList = repositoryUnderTest.findAll();

        assertEquals(0, repositoryUnderTest.count());
        assertTrue(linkList.isEmpty());
    }

    @Test
    void should_find_link_by_id() {
        //given
        entityManager.persist(Link.builder().url("Url1").build());
        Link savedLink = entityManager.persist(Link.builder().url("Url2").build());
        entityManager.persist(Link.builder().url("Url3").build());

        //when
        Link foundLink = repositoryUnderTest.findById(savedLink.getId()).orElse(null);

        //then
        assertNotNull(foundLink);
        assertEquals(savedLink, foundLink);
    }

    @Test
    void should_find_all_links() {
        entityManager.persist(new Link());
        entityManager.persist(new Link());
        entityManager.persist(new Link());

        assertEquals(3, repositoryUnderTest.count());
    }

    @Test
    void should_save_new_link() {
        //given
        Link newLink = Link.builder().url("Url").build();

        //when
        Link savedLink = repositoryUnderTest.save(newLink);

        //then
        assertNotNull(savedLink);
        assertNotNull(savedLink.getId());
        assertEquals("Url", savedLink.getUrl());
    }

    @Test
    void should_save_all_links() {
        //given
        Link link1 = Link.builder().url("Url1").build();
        Link link2 = Link.builder().url("Url2").build();

        //when
        List<Link> savedLinkList = repositoryUnderTest.saveAll(List.of(link1, link2));

        //then
        assertNotNull(savedLinkList);
        assertEquals(2, savedLinkList.size());
    }

    @Test
    void should_delete_link_by_id() {
        //given
        entityManager.persist(Link.builder().url("Url1").build());
        Link linkToDelete = entityManager.persist(Link.builder().url("Url2").build());
        entityManager.persist(Link.builder().url("Url3").build());

        //when
        repositoryUnderTest.deleteById(linkToDelete.getId());

        //then
        assertEquals(2, repositoryUnderTest.count());
        assertNull(repositoryUnderTest.findById(linkToDelete.getId()).orElse(null));
    }

    @Test
    void should_delete_all_links() {
        //given
        entityManager.persist(Link.builder().url("Url1").build());
        entityManager.persist(Link.builder().url("Url2").build());
        entityManager.persist(Link.builder().url("Url3").build());

        //when
        repositoryUnderTest.deleteAll();

        //then
        assertEquals(0, repositoryUnderTest.count());
    }
}