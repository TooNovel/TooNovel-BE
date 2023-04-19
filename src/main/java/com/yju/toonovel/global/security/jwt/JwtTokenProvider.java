package com.yju.toonovel.global.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yju.toonovel.global.security.jwt.dto.RefreshTokenCreateRequestDto;
import com.yju.toonovel.global.security.jwt.exception.ExpiredTokenException;
import com.yju.toonovel.global.security.jwt.exception.InvalidTokenException;
import com.yju.toonovel.global.security.jwt.repository.RefreshTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	private final String secretKey;
	private final long accessTokenExpireSeconds;
	private final long refreshTokenExpireSeconds;
	private final RefreshTokenRepository refreshTokenRepository;

	private final String bearerType = "Bearer";

	@Value("${jwt.header.access-token}")
	String accessTokenHeader;

	@Value("${jwt.header.refresh-token}")
	String refreshTokenHeader;

	public JwtTokenProvider(
		@Value("${jwt.secret-key}") String secretKey,
		@Value("${jwt.expire-seconds.access-token}") long accessTokenExpireSeconds,
		@Value("${jwt.expire-seconds.refresh-token}") long refreshTokenExpireSeconds,
		RefreshTokenRepository refreshTokenRepository) {
		this.secretKey = secretKey;
		this.accessTokenExpireSeconds = accessTokenExpireSeconds;
		this.refreshTokenExpireSeconds = refreshTokenExpireSeconds;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public String createAccessToken(Long userId, String role) {
		Map<String, Object> claims = Map.of("userId", userId, "role", role);
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + accessTokenExpireSeconds * 1000L);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.compact();
	}

	public String createRefreshToken() {
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + refreshTokenExpireSeconds * 1000L);
		return Jwts.builder()
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.compact();
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public Optional<String> extractAccessToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(accessTokenHeader))
			.filter(accessToken -> accessToken.startsWith(bearerType))
			.map(accessToken -> accessToken.replace(bearerType, ""));
	}

	public Optional<String> extractRefreshToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(refreshTokenHeader))
			.filter(refreshToken -> refreshToken.startsWith(bearerType))
			.map(refreshToken -> refreshToken.replace(bearerType, ""));
	}

	public void sendAccessToken(HttpServletResponse response, String accessToken) {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader(accessTokenHeader, accessToken);
	}

	public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader(accessTokenHeader, accessToken);
		response.setHeader(refreshTokenHeader, refreshToken);
	}

	public void updateRefreshToken(Long userId, String refreshToken) {
		refreshTokenRepository.findByUserId(userId)
			.ifPresentOrElse(
				token -> token.update(refreshToken),
				() -> saveRefreshToken(userId, refreshToken)
			);
	}

	public void saveRefreshToken(Long userId, String refreshToken) {
		RefreshTokenCreateRequestDto refreshTokenCreateRequestDto = RefreshTokenCreateRequestDto.builder()
			.userId(userId)
			.refreshToken(refreshToken)
			.build();
		refreshTokenRepository.save(refreshTokenCreateRequestDto.toEntity());
	}

	public void validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
				.build()
				.parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new ExpiredTokenException();
		} catch (JwtException | IllegalArgumentException e) {
			throw new InvalidTokenException();
		}
	}

}
