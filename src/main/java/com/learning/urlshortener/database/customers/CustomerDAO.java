package com.learning.urlshortener.database.customers;

import com.learning.urlshortener.domain.Customer;

public interface CustomerDAO {

    Customer findCustomerById(Long id);

    Customer saveCustomer(Customer customer);

    void deleteCustomerById(Long id);
}
