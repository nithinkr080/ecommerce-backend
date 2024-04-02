package com.ecommerce.api.respository;

import com.ecommerce.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
            value = "SELECT \n" +
                    "    p.product_id,\n" +
                    "    p.image,\n" +
                    "    p.name AS product_name,\n" +
                    "    p.description,\n" +
                    "    p.price,\n" +
                    "    p.category_id AS category_id,\n" +
                    "    c.category_name,\n" +
                    "    s.seller_id,\n" +
                    "    s.company_name,\n" +
                    "    u.user_id,\n" +
                    "    u.username AS seller_username,\n" +
                    "    u.email AS seller_email\n" +
                    "FROM Product p \n" +
                    "LEFT JOIN Category c ON c.category_id = p.category_id\n" +
                    "LEFT JOIN Seller s ON s.seller_id = p.seller_id \n" +
                    "LEFT JOIN User u ON u.user_id = s.user_id;", nativeQuery = true
    )
    List<Product> getProductDetails();
}
