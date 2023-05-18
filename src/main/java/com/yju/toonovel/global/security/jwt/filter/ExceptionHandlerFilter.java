package com.yju.toonovel.global.security.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yju.toonovel.global.error.ErrorResponse;
import com.yju.toonovel.global.security.jwt.exception.TokenException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (TokenException e) {
			log.warn(e.getMessage());
			setErrorResponse(response, e);
		}
	}

	private void setErrorResponse(HttpServletResponse response, TokenException e) throws IOException {
		response.setStatus(e.getErrorCode().getStatus().value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter()
			.write(objectMapper.writeValueAsString(ErrorResponse.of(e.getErrorCode())));
	}
}
