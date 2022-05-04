package com.learning.urlshortener.database;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

@Component
public class SimpleTestObjectFactory {

    public static Customer getSimpleCustomer() {
        String uniqueID = UUID.randomUUID().toString();
        return Customer.builder().nickname("name" + uniqueID).build();
    }

    public static Link getSimpleLink() {
        String uniqueID = UUID.randomUUID().toString();
        return Link.builder()
                .shortenedUrl("http://example1.com/" + uniqueID)
                .url("http://example2.com/" + uniqueID)
                .build();
    }
}
