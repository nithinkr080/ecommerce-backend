package com.ecommerce.api.service;

import com.ecommerce.api.model.Customer;
import com.ecommerce.api.util.response.ApiResponse;

public interface CustomerService {
    Customer getUserById(long id);
}
