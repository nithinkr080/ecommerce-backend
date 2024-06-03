package com.ecommerce.api.service;

import com.ecommerce.api.dto.cart.CartDTO;
import com.ecommerce.api.dto.order.OrderDTO;
import com.ecommerce.api.dto.product.ProductDetailsDTO;
import com.ecommerce.api.util.response.ApiResponse;

import java.util.ArrayList;

public interface ProductService {
    ApiResponse getAllProducts(String categoryName, String sellerId);

    ApiResponse getCartDetailsById(Long userId);

    ApiResponse updateCart(CartDTO cartDTO);

    ApiResponse insertProduct(ProductDetailsDTO productDetailsDTO);

    ApiResponse deleteProduct(Long ProductId);

    ApiResponse updateOrder(CartDTO cartDTO);

    ApiResponse getOrders(Long userId);

    ApiResponse cancelOrder(String orderId, Long userId);
}
