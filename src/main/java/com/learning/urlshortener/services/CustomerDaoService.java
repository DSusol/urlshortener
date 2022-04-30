package com.learning.urlshortener.services;

import java.util.List;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

public interface CustomerDaoService {

    Customer findCustomerByNickname(String nickname);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Customer customer, String newNickname);

    void deleteCustomer(Customer customer);

    List<Link> findAllLinksByCustomer(Customer customer);
}
