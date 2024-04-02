package com.ecommerce.api.respository;

import com.ecommerce.api.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT * from Customer c where c.user_id = :id", nativeQuery = true)
    Customer getCustomerByUserId(Long id);
}
