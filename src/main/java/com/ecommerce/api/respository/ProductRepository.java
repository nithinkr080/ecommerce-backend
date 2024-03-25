package com.ecommerce.api.respository;

import com.ecommerce.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product getAllProduct();
}
