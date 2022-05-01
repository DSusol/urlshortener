package com.learning.urlshortener.database.links;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.learning.urlshortener.domain.Link;

class LinkEntityMapperTest {

    LinkEntityMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = LinkEntityMapper.INSTANCE;
    }

    @Test
    void linkEntity_to_link_conversion() {
        //given
        LinkEntity linkEntity = LinkEntity.builder()
                .shortenedUrl("http://slink.exm/")
                .url("http://example.com/long_link/")
                .clickCount(5)
                .build();

        //when
        Link link = underTest.linkEntityToLink(linkEntity);

        //then
        assertNotNull(link);
        assertThat(link.getShortenedUrl()).isEqualTo("http://slink.exm/");
        assertThat(link.getUrl()).isEqualTo("http://example.com/long_link/");
        assertThat(link.getClickCount()).isEqualTo(5);
    }

    @Test
    void link_to_linkEntity_conversion() {
        //given
        Link link = Link.builder()
                .shortenedUrl("http://slink.exm/")
                .url("http://example.com/long_link/")
                .clickCount(5)
                .build();

        //when
        LinkEntity linkEntity = underTest.linkToLinkEntity(link);

        //then
        assertNotNull(linkEntity);
        assertThat(linkEntity.getShortenedUrl()).isEqualTo("http://slink.exm/");
        assertThat(linkEntity.getUrl()).isEqualTo("http://example.com/long_link/");
        assertThat(linkEntity.getClickCount()).isEqualTo(5);
    }
}