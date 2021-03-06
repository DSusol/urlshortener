package com.learning.urlshortener.database.links;

import java.util.List;
import java.util.Optional;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

public interface LinkDAO {

    Optional<Link> findLinkById(Long id);

    Optional<Link> findLinkByToken(String token);

    boolean existsLinkEntitiesByCustomerAndUrl(Customer customer, String url);

    List<Link> findAllLinksByCustomer(Customer customer);

    Link saveLink(Customer customer, Link link);

    void deleteLinkById(Long id);
}
