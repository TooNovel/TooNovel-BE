package com.yju.toonovel.global.security.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.yju.toonovel.global.security.jwt.JwtAuthenticationToken;
import com.yju.toonovel.global.security.jwt.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String accessToken = tokenService.getAccessToken(request).orElse(null);
		if (accessToken != null) {
			JwtAuthenticationToken authenticationToken = tokenService.getAuthentication(accessToken);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public boolean shouldNotFilter(HttpServletRequest request) {
		return request.getRequestURI().equals("api/v1/token");

	}
}
