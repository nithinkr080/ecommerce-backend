package com.ecommerce.api.respository;

import com.ecommerce.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :emailId")
    User getByUserEmailId(String emailId);

    @Query("SELECT u.username FROM User u WHERE u.email = :emailId")
    String findNameByEmail(String emailId);

    boolean existsByEmail(String emailId);

    boolean existsByEmailAndPassword(String email, String password);
}