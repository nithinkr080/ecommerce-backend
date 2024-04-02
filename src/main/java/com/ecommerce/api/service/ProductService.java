package com.ecommerce.api.service;

import com.ecommerce.api.dto.cart.CartDTO;
import com.ecommerce.api.model.Product;
import com.ecommerce.api.model.ProductDetails;
import com.ecommerce.api.util.response.ApiResponse;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    ApiResponse getAllProducts();

    ApiResponse getCartDetailsById(Long userId);

    ApiResponse updateCart(CartDTO cartDTO);

}
