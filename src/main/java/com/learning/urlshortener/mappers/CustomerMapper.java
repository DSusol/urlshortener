package com.learning.urlshortener.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learning.urlshortener.domain.Customer;
import com.learning.urlshortener.entities.CustomerEntity;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer customerEntityToCustomer(CustomerEntity customerEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "links", ignore = true)
    CustomerEntity customerToCustomerEntity(Customer customer);
}
