package com.bharathi.userservice.repositories;

import com.bharathi.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndDeleted(String value, boolean isDeleted);
}
