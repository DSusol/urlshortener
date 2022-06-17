package com.learning.urlshortener;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.urlshortener.bot.testbot.ExecutedTgTestMethodsRegistry;
import com.learning.urlshortener.bot.testbot.UrlShortenerTestBot;
import com.learning.urlshortener.services.UrlShortenerService;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class BaseFullContextTest extends TestContainerSupplier {

    @Autowired
    protected UrlShortenerTestBot underTest;

    @Autowired
    protected ExecutedTgTestMethodsRegistry executedUpdates;

    @Autowired
    protected UrlShortenerService urlShortenerService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    protected void setUp() {
        executedUpdates.clearAllSendMessages();
    }

    protected void executeUpdate(Update update) {
        underTest.onUpdatesReceived(List.of(update));
    }

    protected String extractUrlFromBotNewLinkResponse(String response) {
        return response.substring(response.indexOf("\n") + 1);
    }
}