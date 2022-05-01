package com.learning.urlshortener.database.links;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

public interface LinkDAO {

    Link findLinkById(Long id);

    Link saveLink(Customer customer, Link link);

    void deleteLinkById(Long id);
}
