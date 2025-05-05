package com.berber_co.barber.repository;

import com.berber_co.barber.entity.Token;
import com.berber_co.barber.entity.user.User;
import com.berber_co.barber.enums.TokenStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByUserAndStatus(User user, TokenStatusEnum tokenStatusEnum);

    Optional<Token> findByRefreshToken(String token);
}
