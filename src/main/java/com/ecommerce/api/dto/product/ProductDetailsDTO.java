package com.ecommerce.api.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDetailsDTO {
    private Long productId;
    private String image;
    private String productName;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private String categoryName;
    private Long sellerId;
    private String companyName;
    private Long userId;
    private String sellerUsername;
    private String sellerEmail;
}