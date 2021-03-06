package com.learning.urlshortener.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    /**
     * Unique identifier.
     * Controller by database.
     */
    private Long id;

    /**
     * User chatId. Must be unique.
     */
    private Long chatId;

}