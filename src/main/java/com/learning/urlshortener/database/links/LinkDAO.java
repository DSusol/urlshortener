package com.learning.urlshortener.database.links;

import java.util.List;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

public interface LinkDAO {

    Link findLinkById(Long id);

    Link findLinkByShortenedUrl(String shortenedUrl);

    List<Link> findAllLinksByCustomer(Customer customer);

    Link saveLink(Customer customer, Link link);

    void deleteLinkById(Long id);
}
