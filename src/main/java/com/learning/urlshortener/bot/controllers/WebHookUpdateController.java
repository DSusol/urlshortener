package com.learning.urlshortener.bot.controllers;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.learning.urlshortener.bot.UrlShortenerBot;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WebHookUpdateController {

    private final UrlShortenerBot urlShortenerBot;

    @PostMapping("/updates/${telegram-bot.token}")
    @ResponseStatus(OK)
    public void receiveUpdate(@RequestBody Update update) {
        urlShortenerBot.onUpdatesReceived(List.of(update));
    }
}