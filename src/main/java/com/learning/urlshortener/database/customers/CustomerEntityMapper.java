package com.learning.urlshortener.database.customers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.learning.urlshortener.domain.Customer;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {

    CustomerEntityMapper INSTANCE = Mappers.getMapper(CustomerEntityMapper.class);

    Customer customerEntityToCustomer(CustomerEntity customerEntity);

    @Mapping(target = "links", ignore = true)
    CustomerEntity customerToCustomerEntity(Customer customer);
}
