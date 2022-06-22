package com.learning.urlshortener.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import com.learning.urlshortener.BaseFullContextTest;

import lombok.SneakyThrows;

class RestExceptionHandlerTest extends BaseFullContextTest {

    @Test
    @SneakyThrows
    public void when_requesting_url_by_invalid_token_should_respond_with_bad_request_status() {

        MvcResult result = mockMvc.perform(get("/invalid_token"))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("message", "status", "status_code");
    }
}