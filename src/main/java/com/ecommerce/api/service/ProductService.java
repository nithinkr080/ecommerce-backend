package com.ecommerce.api.service;

import com.ecommerce.api.dto.cart.CartDTO;
import com.ecommerce.api.util.response.ApiResponse;

public interface ProductService {
    ApiResponse getAllProducts(String categoryName);

    ApiResponse getCartDetailsById(Long userId);

    ApiResponse updateCart(CartDTO cartDTO);

}
