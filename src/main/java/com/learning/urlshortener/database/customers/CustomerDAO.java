package com.learning.urlshortener.database.customers;

import java.util.List;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.domain.Link;

public interface CustomerDAO {

    Customer findCustomerById(Long id);

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Customer customerToUpdate);

    void deleteCustomerById(Long id);

    List<Link> findAllLinksByCustomer(Customer customer);
}
