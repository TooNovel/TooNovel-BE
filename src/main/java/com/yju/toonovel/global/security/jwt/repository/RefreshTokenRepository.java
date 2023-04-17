package com.yju.toonovel.global.security.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yju.toonovel.global.security.jwt.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

	Optional<RefreshToken> findByUserId(Long refreshToken);
}
