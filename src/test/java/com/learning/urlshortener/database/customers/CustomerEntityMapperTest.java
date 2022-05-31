package com.learning.urlshortener.database.customers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.learning.urlshortener.domain.Customer;

class CustomerEntityMapperTest {

    CustomerEntityMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = CustomerEntityMapper.INSTANCE;
    }

    @Test
    void customerEntity_to_customer_conversion() {
        //given
        CustomerEntity customerEntity = CustomerEntity.builder().chatId(2L).build();

        //when
        Customer customer = underTest.customerEntityToCustomer(customerEntity);

        //then
        assertNotNull(customer);
        assertThat(customer.getChatId()).isEqualTo(2L);
    }

    @Test
    void customer_to_customerEntity_conversion() {
        //given
        Customer customer = Customer.builder().chatId(2L).build();

        //when
        CustomerEntity customerEntity = underTest.customerToCustomerEntity(customer);

        //then
        assertNotNull(customerEntity);
        assertThat(customerEntity.getChatId()).isEqualTo(2L);
    }
}