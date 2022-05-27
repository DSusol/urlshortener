package com.learning.urlshortener.bot;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.TestContainerSupplier;
import com.learning.urlshortener.bot.testbot.ExecutedTgTestMethodsRegistry;
import com.learning.urlshortener.bot.testbot.UrlShortenerTestBot;
import com.learning.urlshortener.services.BotServices;

@SpringBootTest
public class BaseFullContextTest extends TestContainerSupplier {

    @Value("${base.domain}")
    protected String baseDomain;

    @Autowired
    protected UrlShortenerTestBot underTest;

    @Autowired
    protected BotServices botServices;

    @Autowired
    protected ExecutedTgTestMethodsRegistry executedUpdates;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @BeforeEach
    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        executedUpdates.clearAllSendMessages();
    }

    protected void executeUpdate(Update update) {
        underTest.onUpdatesReceived(List.of(update));
    }

    protected String extractRelativeUrlFromNewLinkCommandResponse(String response) {
        return "/" + response.substring(response.lastIndexOf("/") + 1);
    }
}