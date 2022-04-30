package com.learning.urlshortener.services;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

public interface LinkDaoService {

    Link findLinkByShortenedUrl(String shortenedUrl);

    Link saveLink(Customer customer, Link link);

    void deleteLink(Link link);
}
