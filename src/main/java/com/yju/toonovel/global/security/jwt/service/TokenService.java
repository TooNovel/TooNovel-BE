package com.yju.toonovel.global.security.jwt.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;
import com.yju.toonovel.global.security.jwt.JwtAuthenticationToken;
import com.yju.toonovel.global.security.jwt.JwtTokenProvider;
import com.yju.toonovel.global.security.jwt.dto.TokenReIssueResponseDto;
import com.yju.toonovel.global.security.jwt.entity.RefreshToken;
import com.yju.toonovel.global.security.jwt.exception.RefreshTokenNotFoundException;
import com.yju.toonovel.global.security.jwt.repository.RefreshTokenRepository;
import com.yju.toonovel.global.util.CookieUtils;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;

	@Value("${jwt.expire-seconds.access-token}")
	int accessTokenExpireSeconds;
	@Value("${jwt.expire-seconds.refresh-token}")
	int refreshTokenExpireSeconds;

	@Transactional
	public TokenReIssueResponseDto reIssueTokens(String token) {

		jwtTokenProvider.validateToken(token);

		RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
			.orElseThrow(() -> new RefreshTokenNotFoundException());

		User user = userRepository.findByUserId(refreshToken.getUserId())
			.orElseThrow(() -> new UserNotFoundException());

		String reIssuedAccessToken = reIssueAccessToken(user.getUserId(), user.getRole().name());
		String reIssuedRefreshToken = reIssueRefreshToken(user.getUserId());

		String accessTokenCookie = createAccessTokenCookie(reIssuedAccessToken);
		String refreshTokenCookie = createRefreshTokenCookie(reIssuedRefreshToken);

		return new TokenReIssueResponseDto(accessTokenCookie, refreshTokenCookie);

	}

	public String reIssueAccessToken(Long userId, String role) {
		return jwtTokenProvider.createAccessToken(userId, role);
	}

	@Transactional
	public String reIssueRefreshToken(Long userId) {
		String token = jwtTokenProvider.createRefreshToken();
		RefreshToken reIssuedRefreshToken = new RefreshToken(userId, token);

		return refreshTokenRepository.save(reIssuedRefreshToken).getRefreshToken();
	}

	private String createAccessTokenCookie(String accessToken) {
		return CookieUtils.addAccessTokenCookie("accessTokenCookie", accessToken, accessTokenExpireSeconds);
	}

	private String createRefreshTokenCookie(String refreshToken) {
		return CookieUtils.addRefreshTokenCookie("refreshTokenCookie", refreshToken, refreshTokenExpireSeconds);
	}

	public Optional<String> getAccessToken(HttpServletRequest request) {
		String accessToken = jwtTokenProvider.extractAccessToken(request).orElse(null);
		if (accessToken != null) {
			jwtTokenProvider.validateToken(accessToken);
		}
		return Optional.ofNullable(accessToken);
	}

	public JwtAuthenticationToken getAuthentication(String accessToken) {

		Claims claims = jwtTokenProvider.getClaims(accessToken);

		Long userId = claims.get("userId", Long.class);
		String role = claims.get("role", String.class);

		JwtAuthentication principal = new JwtAuthentication(accessToken, userId, role);
		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

		return new JwtAuthenticationToken(principal, null, authorities);
	}

}
