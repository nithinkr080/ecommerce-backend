package com.ecommerce.api.service;

import com.ecommerce.api.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer getCustomerByUserId(Long id);
}
