package com.walletapp.ewallet.repository;

import com.walletapp.ewallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Method to find a user by username
    Optional<User> findByUsername(String username);

    Optional<User> findByPhoneNumber(Long phoneNumber);
   // Optional<User> findByPhoneNumber(Long phoneNumber);


}
