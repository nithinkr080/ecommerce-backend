package com.ecommerce.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "Customer")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {
    @Id
    @Column(name = "customer_id")
    @Getter
    @Setter
    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private Users users;

    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @Column(name = "address")
    @Getter
    @Setter
    private String address;

    @Column(columnDefinition = "json")
    private String cart;


    // Get cart in list
    public List<Long> getCart() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(cart, new TypeReference<List<Long>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Set cart in list
    public void setCart(List<Long> cart) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.cart = objectMapper.writeValueAsString(cart);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}