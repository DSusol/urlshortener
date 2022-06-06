package com.learning.urlshortener.bot.commands.main;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.BaseFullContextTest;
import com.learning.urlshortener.bot.BotTestUtils;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CreateNewLinkFlowTest extends BaseFullContextTest {

    private final Long CHAT_ID = 666L;

    @Test
    @Order(1)
    void when_sending_new_link_command_should_prompt_url() {
        Update update = BotTestUtils.createCommandUpdateWithMessageFromChat(CHAT_ID, "/new_link");

        executeUpdate(update);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(CHAT_ID);
        assertThat(savedMessageText).contains("Please provide full link:");
    }

    @Test
    @Order(2)
    void when_invalid_url_is_provided_should_ask_for_another_input() {
        Update update = BotTestUtils.createUpdateWithMessageFromChat(CHAT_ID, "invalid_url");

        executeUpdate(update);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(CHAT_ID);
        assertThat(savedMessageText).contains("invalid url provided, please try again:");
    }

    @Test
    @Order(3)
    void when_url_is_provided_should_obtain_shortened_link() throws Exception {
        Update update = BotTestUtils.createUpdateWithMessageFromChat(CHAT_ID, "https://www.longurl.com/");

        executeUpdate(update);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(CHAT_ID);
        assertThat(savedMessageText).contains("here is your shortened link:");

        String shortenedUrl = extractUrlFromBotNewLinkResponse(savedMessageText);
        mockMvc.perform(get(shortenedUrl))
                .andExpect(status().isPermanentRedirect())
                .andExpect(redirectedUrl("https://www.longurl.com/"));
    }
}