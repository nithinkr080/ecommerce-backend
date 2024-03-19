package com.ecommerce.api.respository;

import com.ecommerce.api.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query("INSERT INTO User u (u.email, u.username, u.password, u.role) VALUES (:emailId, :username, :password, :role)")
    @Transactional
    int insertUser(String emailId, String username, String password, String role);
}