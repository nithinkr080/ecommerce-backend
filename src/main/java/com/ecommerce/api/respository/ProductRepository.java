package com.ecommerce.api.respository;

import com.ecommerce.api.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
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
                    "LEFT JOIN User u ON u.user_id = s.user_id where c.category_name LIKE %:categoryName% and s.seller_id LIKE %:sellerId%", nativeQuery = true
    )

    List<Product> getProductDetails(String categoryName, String sellerId);


    @Modifying
    @Query(value = "INSERT INTO product (seller_id, name, description, price, category_id, image)\n" +
            "VALUES (:sellerId, :name ,:description , :price, :categoryId, :image)", nativeQuery = true)
    @Transactional
    int insertProduct(Long sellerId, String name, String description, BigDecimal price, Long categoryId, String image);

    @Modifying
    @Query(value = "DELETE FROM Product WHERE product_id = :productId", nativeQuery = true)
    @Transactional
   int deleteProduct(Long productId);

    @Modifying
    @Query(value = "UPDATE Customer SET cart = :cartList WHERE user_id = :userId", nativeQuery = true)
    @Transactional
    int removeCartProduct(@Param("userId") Long userId, @Param("cartList") String cartList);

}
