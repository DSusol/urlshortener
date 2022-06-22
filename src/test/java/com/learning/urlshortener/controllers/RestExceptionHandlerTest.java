package com.learning.urlshortener.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.learning.urlshortener.bot.ShallowAdapterConfig;
import com.learning.urlshortener.services.UrlShortenerService;

import lombok.SneakyThrows;

class RestExceptionHandlerTest extends ShallowAdapterConfig {

    @Autowired
    UrlShortenerService urlShortenerService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void when_throws_unexpected_exception_should_respond_with_internal_server_error_status() {

        when(urlShortenerService.findUrlByToken(any())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/token"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("status_code").value(500));
    }

    @Test
    @SneakyThrows
    public void when_requesting_sort_url_with_invalid_token_should_respond_with_not_found_status() {

        when(urlShortenerService.findUrlByToken(any())).thenThrow(new IllegalArgumentException());

        mockMvc.perform(get("/invalid_token"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").value("NOT_FOUND"))
                .andExpect(jsonPath("status_code").value(404));
    }
}