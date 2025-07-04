package com.sch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sch.entity.Token;
import com.sch.enums.TokenStatus;

public interface TokenRepository extends JpaRepository<Token, Long> {

	Optional<Token> findFirstByStatusOrderByCreatedAtAsc(TokenStatus called);

	List<Token> findByStatusOrderByCreatedAtAsc(TokenStatus waiting);

}
