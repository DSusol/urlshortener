package com.learning.urlshortener.bot.commands.main.create_new_link.validation;

public interface UValidator {

    boolean isNotValid(String url);

    UrlValidationStatus getUrlValidationStatus();
}
