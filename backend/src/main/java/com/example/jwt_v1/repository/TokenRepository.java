package com.example.jwt_v1.repository;

import com.example.jwt_v1.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("select t from Token t where t.user.id = ?1 and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokensByUser(Long userId);

    Optional<Token> findByToken(String token);
}
