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
    void when_invalid_url_name_is_provided_should_ask_for_another_input() {
        Update update = BotTestUtils.createUpdateWithMessageFromChat(CHAT_ID, "invalid_url");

        executeUpdate(update);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(CHAT_ID);
        assertThat(savedMessageText).contains("invalid url provided, please try again:");
    }

    @Test
    @Order(3)
    void when_url_is_provided_should_obtain_shortened_link() throws Exception {
        Update update = BotTestUtils.createUpdateWithMessageFromChat(CHAT_ID, "https://www.the.longest.url.you.ever.saw.com/");

        executeUpdate(update);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(CHAT_ID);
        assertThat(savedMessageText).contains("here is your shortened link:");

        String shortenedUrl = extractUrlFromBotNewLinkResponse(savedMessageText);
        mockMvc.perform(get(shortenedUrl))
                .andExpect(status().isPermanentRedirect())
                .andExpect(redirectedUrl("https://www.the.longest.url.you.ever.saw.com/"));
    }

    @Test
    @Order(4)
    void when_bot_is_not_able_to_make_url_shorter_then_send_related_message() {
        Update update = BotTestUtils.createCommandUpdateWithMessageFromChat(CHAT_ID, "/new_link");
        executeUpdate(update);
        update = BotTestUtils.createUpdateWithMessageFromChat(CHAT_ID, "https://www.srt.ru/");
        executeUpdate(update);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(CHAT_ID);
        assertThat(savedMessageText).contains("im not able to make it shorter");
    }

    @Test
    @Order(5)
    void default_chat_state_verification() {
        Update update = BotTestUtils.createUpdateWithMessageFromChat(CHAT_ID, "am I in new link creation stage still?");

        executeUpdate(update);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId(CHAT_ID);
        assertThat(savedMessageText).contains("The command is not recognized. See /help for available options.");
    }
}