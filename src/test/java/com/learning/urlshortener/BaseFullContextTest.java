package com.learning.urlshortener;

import static org.assertj.core.api.Assertions.fail;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.urlshortener.bot.testbot.ExecutedTgTestMethodsRegistry;
import com.learning.urlshortener.bot.testbot.UrlShortenerTestBot;
import com.learning.urlshortener.services.UrlShortenerService;

import lombok.SneakyThrows;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class BaseFullContextTest extends TestContainerSupplier {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UrlShortenerTestBot underTest;

    @Autowired
    protected ExecutedTgTestMethodsRegistry executedUpdates;

    @Autowired
    protected UrlShortenerService urlShortenerService;

    @Autowired
    protected MockMvc mockMvc;

    @Value("${telegram-bot.token}")
    private String botToken;

    @Value("${telegram-bot.receive.update.mode}")
    private String receiveUpdateOption;

    @BeforeEach
    protected void setUp() {
        executedUpdates.clearAllSendMessages();
    }

    @SneakyThrows
    protected void executeUpdate(Update update) {
        if (!receiveUpdateOption.equals("webhook")) {
            fail("Set up 'telegram-bot.receive.update.mode' test property to 'webhook'");
        }

        mockMvc.perform(post("/updates/" + botToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)));
    }

    protected String extractUrlFromBotNewLinkResponse(String response) {
        return response.substring(response.indexOf("\n") + 1);
    }
}