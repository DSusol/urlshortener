package com.learning.urlshortener.controllers;

import static org.springframework.http.HttpStatus.PERMANENT_REDIRECT;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.learning.urlshortener.services.urlshortener.UrlShortenerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class LinkController {

    private final UrlShortenerService urlShortenerService;

    @GetMapping("/{token}")
    @ResponseStatus(PERMANENT_REDIRECT)
    public RedirectView redirectToFullUrl(@PathVariable String token) {
        return new RedirectView(urlShortenerService.findUrlByToken(token));
    }
}