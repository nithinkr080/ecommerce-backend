package com.ecommerce.api.service;

import com.ecommerce.api.dto.user.ProductDTO;
import com.ecommerce.api.model.Product;

public interface ProductService {
    ProductDTO getAllProducts(ProductDTO productDTO);
}
