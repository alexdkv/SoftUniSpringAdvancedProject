package com.finalProject.SoftUniProject.repository;

import com.finalProject.SoftUniProject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findByEmail(String email);

}
