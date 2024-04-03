package com.ecommerce.api.respository;

import com.ecommerce.api.model.ProductDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long> {
    @Query(value = "select p.product_id , p.name as product_name, p.description , p.seller_id ,\n" +
            "p.category_id , p.image, p.price\n" +
            "from Product p where p.product_id = :id", nativeQuery = true)
    Optional<ProductDetails> getProductById(Long id);

    @Modifying
    @Query(value = "update Customer SET cart = :cartId where user_id = :id", nativeQuery = true)
    @Transactional
    int updateCart(String cartId, Long id);
}
