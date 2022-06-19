package com.learning.urlshortener.bot.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.urlshortener.BaseFullContextTest;
import com.learning.urlshortener.bot.BotTestUtils;

class WebHookUpdateControllerTest extends BaseFullContextTest {

    @Value("${telegram-bot.token}")
    private String botToken;

    @Value("${telegram-bot.receive.update.mode}")
    private String receiveUpdateOption;

    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    void when_requesting_help_through_update_post_method_should_show_help_response() throws Exception {
        if(!receiveUpdateOption.equals("webhook")) {
            return;
        }

        //given
        Update update = BotTestUtils.createCommandUpdateWithMessageFromChat(666L, "/help");
        update.getMessage().getFrom().setLanguageCode("en");

        //when
        mockMvc.perform(post("/updates/" + botToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)));

        //then
        assertThat(executedUpdates.getAllSendMessagesForChatId(666L)).hasSize(1);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(666L);
        assertThat(savedMessageText).contains("Available commands:");
    }
}