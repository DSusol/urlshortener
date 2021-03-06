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
                .token("123456")
                .url("http://example.com/long_link/")
                .clickCount(5)
                .build();

        //when
        Link link = underTest.linkEntityToLink(linkEntity);

        //then
        assertNotNull(link);
        assertThat(link.getToken()).isEqualTo("123456");
        assertThat(link.getUrl()).isEqualTo("http://example.com/long_link/");
        assertThat(link.getClickCount()).isEqualTo(5);
    }

    @Test
    void link_to_linkEntity_conversion() {
        //given
        Link link = Link.builder()
                .token("123456")
                .url("http://example.com/long_link/")
                .clickCount(5)
                .build();

        //when
        LinkEntity linkEntity = underTest.linkToLinkEntity(link);

        //then
        assertNotNull(linkEntity);
        assertThat(linkEntity.getToken()).isEqualTo("123456");
        assertThat(linkEntity.getUrl()).isEqualTo("http://example.com/long_link/");
        assertThat(linkEntity.getClickCount()).isEqualTo(5);
    }
}