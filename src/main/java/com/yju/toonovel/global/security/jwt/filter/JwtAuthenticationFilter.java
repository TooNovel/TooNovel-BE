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
import org.springframework.web.filter.OncePerRequestFilter;

import com.yju.toonovel.global.security.jwt.JwtAuthentication;
import com.yju.toonovel.global.security.jwt.JwtAuthenticationToken;
import com.yju.toonovel.global.security.jwt.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		Optional<String> accessToken = jwtTokenProvider.extractAccessToken(request);
		if (accessToken != null) {
			jwtTokenProvider.validateToken(String.valueOf(accessToken));
			JwtAuthenticationToken authenticationToken = getAuthentication(String.valueOf(accessToken));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}

		filterChain.doFilter(request, response);

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
