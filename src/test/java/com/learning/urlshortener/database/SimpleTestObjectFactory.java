package com.learning.urlshortener.database;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import java.util.Random;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

public class SimpleTestObjectFactory {

    public static Customer getSimpleCustomer() {
        return Customer.builder().chatId(new Random().nextLong()).build();
    }

    public static Link getSimpleLink() {
        return Link.builder()
                .token(randomAlphanumeric(6))
                .url("http://example.com/" + randomAlphanumeric(10))
                .build();
    }
}
