package com.bharathi.userservice.repositories;

import com.bharathi.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
