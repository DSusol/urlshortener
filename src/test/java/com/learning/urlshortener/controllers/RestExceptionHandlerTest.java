package com.learning.urlshortener.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.learning.urlshortener.services.UrlShortenerService;

import lombok.SneakyThrows;

class RestExceptionHandlerTest {

    @Mock
    UrlShortenerService urlShortenerService;

    @InjectMocks
    LinkController linkController;

    RestExceptionHandler underTest;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        underTest = new RestExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(linkController).setControllerAdvice(underTest).build();
    }

    @Test
    @SneakyThrows
    public void when_throws_unexpected_exception_should_respond_with_internal_server_error_status() {

        when(urlShortenerService.findUrlByToken(any())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/token"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("status_code").value(500));
    }
}