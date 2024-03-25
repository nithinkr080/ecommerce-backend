package com.ecommerce.api.service.impl;

import com.ecommerce.api.dto.user.ProductDTO;
import com.ecommerce.api.model.Product;
import com.ecommerce.api.service.ProductService;

public class ProductServiceImpl implements ProductService {
    @Override
    public ProductDTO getAllProduct(ProductDTO productDTO) {
        return productDTO;
    }
}
