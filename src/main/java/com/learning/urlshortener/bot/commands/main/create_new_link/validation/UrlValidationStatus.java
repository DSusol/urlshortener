package com.learning.urlshortener.bot.commands.main.create_new_link.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * List of url input statuses.
 * <p></p>
 * <p><i>/VALID</i> - create_new_link command proceeds with short link creation</p>
 * <p><i>/INVALID_NAME</i> - create_new_link command prompts for new url input</p>
 * <p><i>/SHORT_NAME</i> - create_new_link cannot make url length shorter, the command is aborted</p>
 */
@Getter
@RequiredArgsConstructor
public enum UrlValidationStatus {

    VALID("", false),
    INVALID_NAME("new.link.command.invalid.url", false),
    SHORT_NAME("new.link.command.short.url", true);

    private final String botResponse;
    private final boolean isCommandTermination;
}