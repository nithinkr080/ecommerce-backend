package com.ecommerce.api.service;

import com.ecommerce.api.dto.cart.CartDTO;
import com.ecommerce.api.dto.product.ProductDetailsDTO;
import com.ecommerce.api.util.response.ApiResponse;

import java.util.ArrayList;

public interface ProductService {
    ApiResponse getAllProducts(String categoryName, String sellerId);

    ApiResponse getCartDetailsById(Long userId);

    ApiResponse updateCart(CartDTO cartDTO);

    ApiResponse insertProduct(ProductDetailsDTO productDetailsDTO);

    ApiResponse deleteProduct(Long ProductId);

}
