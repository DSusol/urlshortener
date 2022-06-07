package com.learning.urlshortener.services.urlshortener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.learning.urlshortener.database.links.LinkDAO;
import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;
import com.learning.urlshortener.services.UrlShortenerServiceImpl;

class UrlShortenerServiceImplTest {

    @Mock
    LinkDAO linkDAO;

    @InjectMocks
    UrlShortenerServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void when_saving_existing_url_then_return_existing_link_without_saving() {
        //given
        Customer customer = new Customer();
        String existingUrlToSave = "https://www.example.com/";
        Link existingLink = new Link();

        //when
        when(linkDAO.findLinkByCustomerAndUrl(customer, existingUrlToSave)).thenReturn(Optional.of(existingLink));
        Link returnedLink = underTest.saveNewLink(customer, existingUrlToSave);

        //then
        assertThat(returnedLink).isEqualTo(existingLink);
        verify(linkDAO, times(0)).saveLink(any(), any());
    }
}