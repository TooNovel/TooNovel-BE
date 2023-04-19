package com.yju.toonovel.global.security.jwt.filter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.yju.toonovel.domain.user.repository.UserRepository;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;
import com.yju.toonovel.global.security.jwt.JwtAuthenticationToken;
import com.yju.toonovel.global.security.jwt.JwtTokenProvider;
import com.yju.toonovel.global.security.jwt.entity.RefreshToken;
import com.yju.toonovel.global.security.jwt.repository.RefreshTokenRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		Optional<String> refreshToken = jwtTokenProvider.extractRefreshToken(request);
		if (refreshToken.isPresent()) {
			jwtTokenProvider.validateToken(String.valueOf(refreshToken));
			reIssueAccessToken(response, String.valueOf(refreshToken));
			return;
		}
		Optional<String> accessToken = getAccessToken(request);
		if (accessToken.isPresent()) {
			JwtAuthenticationToken authenticationToken = getAuthentication(String.valueOf(accessToken));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		filterChain.doFilter(request, response);
	}

	private Optional<String> getAccessToken(HttpServletRequest request) {
		Optional<String> accessToken = jwtTokenProvider.extractAccessToken(request);
		if (accessToken.isPresent()) {
			jwtTokenProvider.validateToken(String.valueOf(accessToken));
		}
		return accessToken;
	}

	public void reIssueAccessToken(HttpServletResponse response, String refreshToken) {
		refreshTokenRepository.findByRefreshToken(refreshToken)
			.ifPresent(token -> {
				String reIssuedRefreshToken = reIssueRefreshToken(token);
				userRepository.findByUserId(token.getUserId())
					.ifPresent(user -> jwtTokenProvider.sendAccessAndRefreshToken(response,
						jwtTokenProvider.createAccessToken(user.getUserId(), user.getRole().name()),
						reIssuedRefreshToken));
			});
	}

	private String reIssueRefreshToken(RefreshToken token) {
		String reIssuedRefreshToken = jwtTokenProvider.createRefreshToken();
		jwtTokenProvider.updateRefreshToken(token.getUserId(), reIssuedRefreshToken);
		refreshTokenRepository.saveAndFlush(token);
		return reIssuedRefreshToken;
	}

	public JwtAuthenticationToken getAuthentication(String accessToken) {

		Claims claims = jwtTokenProvider.getClaims(accessToken);

		Long userId = claims.get("userId", Long.class);
		String role = claims.get("role", String.class);

		JwtAuthentication principal = new JwtAuthentication(accessToken, userId);
		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

		return new JwtAuthenticationToken(principal, null, authorities);
	}
}
