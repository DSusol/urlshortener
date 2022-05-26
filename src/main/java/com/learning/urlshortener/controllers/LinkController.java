package com.learning.urlshortener.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.learning.urlshortener.services.BotServices;

@Controller
public class LinkController {


    private final BotServices botServices;

    public LinkController(BotServices botServices) {
        this.botServices = botServices;
    }

    @RequestMapping("/{token}")
    public RedirectView redirectToFullUrl(@PathVariable String token) {
        return new RedirectView(botServices.findUrlByToken(token));
    }
}