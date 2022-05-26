package com.learning.urlshortener.bot.fullcontext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.bot.BotTestUtils;
import com.learning.urlshortener.domain.Customer;

@SpringBootTest
class UrlShortenerBotTest extends BaseFullContextTest {

    @Test
    void when_sending_invalid_command_should_prompt_help_option() {
        //given
        Update update = BotTestUtils.createUpdateWithMessageFromChat(666L, "invalid command");

        //when
        executeUpdate(update);

        //then
        assertThat(executedUpdates.getAllSendMessagesForChatId("666")).hasSize(1);
        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId("666");
        assertThat(savedMessageText).contains("/help");
    }

    @Test
    void when_requesting_help_should_show_help_response() {
        //given
        Update update = BotTestUtils.createUpdateWithMessageFromChat(666L, "/help");
        update.getMessage().getFrom().setLanguageCode("en");

        //when
        executeUpdate(update);

        //then
        assertThat(executedUpdates.getAllSendMessagesForChatId("666")).hasSize(1);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId("666");
        assertThat(savedMessageText).contains("Available commands:");
    }

    @Test
    void create_new_link_performance_verification() throws Exception {
        // STEP 1 - start using bot to save new customer
        Update update = BotTestUtils.createUpdateWithMessageFromChat(666L, "/start");

        executeUpdate(update);

        Customer savedCustomer = botServices.getCustomerByChatId(666L);
        assertThat(savedCustomer).isNotNull();
        assertThat(executedUpdates.getAllSendMessagesForChatId("666")).hasSize(1);
        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId("666");
        assertThat(savedMessageText).isEqualTo("Hi bro! I can make shortened links for you. See /help for available options.");


        // STEP 2 - sending '/new_link' command without args
        update = BotTestUtils.createCommandUpdateWithMessageFromChat(666L, "/new_link");

        executeUpdate(update);

        assertThat(executedUpdates.getAllSendMessagesForChatId("666")).hasSize(2);
        savedMessageText = executedUpdates.getLastSendMessageTextForChatId("666");
        assertThat(savedMessageText).contains("Please provide full link:");


        // STEP 3 - sending full ulr to get shortened link
        String url1 = "https://longurl_1.com/";
        update = BotTestUtils.createUpdateWithMessageFromChat(666L, url1);

        executeUpdate(update);

        savedMessageText = executedUpdates.getLastSendMessageTextForChatId("666");
        assertThat(savedMessageText).contains("here is your shortened link:\n" + baseDomain);


        // STEP 4 - verifying shortened link redirects to provided url
        String relativeUrl = extractRelativeUrlFromNewLinkCommandResponse(savedMessageText);

        mockMvc.perform(get(relativeUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url1));


        // STEP 5 - sending '/new_link' command with provided url
        String url2 = "https://longurl_2.com/";
        update = BotTestUtils.createCommandUpdateWithMessageFromChat(666L, "/new_link " + url2);

        executeUpdate(update);

        savedMessageText = executedUpdates.getLastSendMessageTextForChatId("666");
        assertThat(savedMessageText).contains("here is your shortened link:\n" + baseDomain);
        relativeUrl = extractRelativeUrlFromNewLinkCommandResponse(savedMessageText);
        mockMvc.perform(get(relativeUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url2));
    }

    // temporary test implementation for the not-yet-incorporated commands:
    @ParameterizedTest(name = "Run {index}: verified command = {0}")
    @MethodSource("commandArgumentProvider")
    void when_requesting_command_should_show_command_response(String command) {
        //given
        Update update = BotTestUtils.createCommandUpdateWithMessageFromChat(666L, command);

        //when
        executeUpdate(update);

        //then
        assertThat(executedUpdates.getAllSendMessagesForChatId("666")).hasSize(1);

        String savedMessageText = executedUpdates.getLastSendMessageTextForChatId("666");
        assertThat(savedMessageText).doesNotContain("/help");
    }

    static Stream<Arguments> commandArgumentProvider() {
        return Stream.of(
                arguments("/show_link"),
                arguments("/my_links"),
                arguments("/delete_link")
        );
    }
}