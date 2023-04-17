package com.kawe.belajarrest.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kawe.belajarrest.entity.Auth;

public interface AuthRepository extends JpaRepository<Auth, Integer> {
    Optional<Auth> findByUsername(String username);
}
