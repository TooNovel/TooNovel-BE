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

		//리프레쉬 토큰이 있으면 재발급이라고 인지하는 부분이 필요없지않나? 토큰 컨트롤러로 갈거
		//필터에서는 오직 인증인가만!!
		String refreshToken = jwtTokenProvider.extractRefreshToken(request).orElse(null);
		if (refreshToken != null) {
			jwtTokenProvider.validateToken(refreshToken);
			reIssueAccessToken(response, refreshToken);
			return;
		}
		String accessToken = getAccessToken(request).orElse(null);
		if (accessToken != null) {
			JwtAuthenticationToken authenticationToken = getAuthentication(accessToken);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		filterChain.doFilter(request, response);
	}

	private Optional<String> getAccessToken(HttpServletRequest request) {
		String accessToken = jwtTokenProvider.extractAccessToken(request).orElse(null);
		if (accessToken != null) {
			jwtTokenProvider.validateToken(accessToken);
		}
		return Optional.ofNullable(accessToken);
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
