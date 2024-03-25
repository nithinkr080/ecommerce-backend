package com.ecommerce.api.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private Long sellerId;
    private String name;
    private String description;
    private Long Price;
    private Long categoryId;
    private String Image;
}
