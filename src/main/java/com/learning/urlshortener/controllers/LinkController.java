package com.learning.urlshortener.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.learning.urlshortener.services.UrlShortenerService;

@Controller
public class LinkController {

    private final UrlShortenerService urlShortenerService;

    public LinkController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @RequestMapping("/{token}")
    public RedirectView redirectToFullUrl(@PathVariable String token) {
        return new RedirectView(urlShortenerService.findUrlByToken(token));
    }
}