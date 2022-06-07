package com.learning.urlshortener.services.urlvalidation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * List of url input statuses.
 * <p></p>
 * <p><i>/VALID</i> - valid url marker</p>
 * <p><i>/INVALID</i> - incorrect url syntax</p>
 * <p><i>/SHORT_NAME</i> - urlshortener app cannot make url shorter</p>
 */
@Getter
@RequiredArgsConstructor
public enum UrlValidationResult {

    VALID,
    INVALID,
    SHORT_NAME
}