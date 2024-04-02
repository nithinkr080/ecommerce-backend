package com.ecommerce.api.service;

import com.ecommerce.api.dto.product.ProductDetailsDTO;
import com.ecommerce.api.model.Product;
import com.ecommerce.api.util.response.ApiResponse;

import java.util.List;

public interface ProductService {
    ApiResponse getAllProducts();

    Product getProductById(Long id);

}
