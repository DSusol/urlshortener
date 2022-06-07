package com.learning.urlshortener.services.urlvalidation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * List of url input statuses.
 * <p></p>
 * <p><i>/VALID</i> - create_new_link command proceeds with short link creation</p>
 * <p><i>/INVALID</i> - create_new_link command prompts for new url input</p>
 * <p><i>/SHORT_NAME</i> - create_new_link cannot make url length shorter, the command is aborted</p>
 */
@Getter
@RequiredArgsConstructor
public enum UrlValidationResult {

    VALID,
    INVALID,
    SHORT_NAME
}