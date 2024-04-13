package com.ecommerce.api.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "Seller")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Seller {
    @Id
    @Column(name = "seller_id")
    private Long sellerId;

    @Column(name = "company_name")
    private String companyName;
}


