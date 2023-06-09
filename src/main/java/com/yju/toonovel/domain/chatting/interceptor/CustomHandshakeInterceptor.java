package com.yju.toonovel.domain.chatting.interceptor;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.yju.toonovel.global.security.jwt.JwtTokenProvider;
import com.yju.toonovel.global.security.jwt.exception.TokenExpiredException;
import com.yju.toonovel.global.security.jwt.exception.TokenInvalidException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomHandshakeInterceptor implements HandshakeInterceptor {
	private final JwtTokenProvider jwtTokenProvider;
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
		Map<String, Object> attributes) {

		String headers = request.getHeaders().toString();
		String temp = headers
			.substring(headers.indexOf("accessTokenCookie=") + 18, headers.length());
		String accessToken = temp.substring(0, temp.indexOf(";"));

		try {
			jwtTokenProvider.validateToken(accessToken);
			return true;
		} catch (TokenExpiredException | TokenInvalidException exception) {
			return false;
		}
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
		Exception exception) {

	}
}
