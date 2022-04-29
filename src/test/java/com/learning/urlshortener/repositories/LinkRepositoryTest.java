package com.learning.urlshortener.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.learning.urlshortener.entities.LinkEntity;

class LinkRepositoryTest extends BaseDBTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    LinkRepository repositoryUnderTest;

    @Test
    void should_find_no_link_if_repository_is_empty() {
        List<LinkEntity> linkList = repositoryUnderTest.findAll();

        assertEquals(0, repositoryUnderTest.count());
        assertTrue(linkList.isEmpty());
    }

    @Test
    void should_find_link_by_id() {
        //given
        entityManager.persist(LinkEntity.builder().url("Url1").build());
        LinkEntity savedLink = entityManager.persist(LinkEntity.builder().url("Url2").build());
        entityManager.persist(LinkEntity.builder().url("Url3").build());

        //when
        LinkEntity foundLink = repositoryUnderTest.findById(savedLink.getId()).orElse(null);

        //then
        assertNotNull(foundLink);
        assertEquals(savedLink, foundLink);
    }

    @Test
    void should_find_all_links() {
        entityManager.persist(new LinkEntity());
        entityManager.persist(new LinkEntity());
        entityManager.persist(new LinkEntity());

        assertEquals(3, repositoryUnderTest.count());
    }

    @Test
    void should_save_new_link() {
        //given
        LinkEntity newLink = LinkEntity.builder().url("Url").build();

        //when
        LinkEntity savedLink = repositoryUnderTest.save(newLink);

        //then
        assertNotNull(savedLink);
        assertNotNull(savedLink.getId());
        assertEquals("Url", savedLink.getUrl());
    }

    @Test
    void should_save_all_links() {
        //given
        LinkEntity link1 = LinkEntity.builder().url("Url1").build();
        LinkEntity link2 = LinkEntity.builder().url("Url2").build();

        //when
        List<LinkEntity> savedLinkList = repositoryUnderTest.saveAll(List.of(link1, link2));

        //then
        assertNotNull(savedLinkList);
        assertEquals(2, savedLinkList.size());
    }

    @Test
    void should_delete_link_by_id() {
        //given
        entityManager.persist(LinkEntity.builder().url("Url1").build());
        LinkEntity linkToDelete = entityManager.persist(LinkEntity.builder().url("Url2").build());
        entityManager.persist(LinkEntity.builder().url("Url3").build());

        //when
        repositoryUnderTest.deleteById(linkToDelete.getId());

        //then
        assertEquals(2, repositoryUnderTest.count());
        assertNull(repositoryUnderTest.findById(linkToDelete.getId()).orElse(null));
    }

    @Test
    void should_delete_all_links() {
        //given
        entityManager.persist(LinkEntity.builder().url("Url1").build());
        entityManager.persist(LinkEntity.builder().url("Url2").build());
        entityManager.persist(LinkEntity.builder().url("Url3").build());

        //when
        repositoryUnderTest.deleteAll();

        //then
        assertEquals(0, repositoryUnderTest.count());
    }
}