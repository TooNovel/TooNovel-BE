package com.yju.toonovel.global.security.jwt;

import com.yju.toonovel.global.security.jwt.exception.TokenInvalidException;

import lombok.ToString;

@ToString
public class JwtAuthentication {

	public final String accessToken;
	public final Long userId;
	public final String role;

	public JwtAuthentication(String accessToken, Long userId, String role) {
		if (accessToken.isEmpty() || accessToken.isBlank()) {
			throw new TokenInvalidException();
		}
		if (userId <= 0 || userId == null) {
			throw new TokenInvalidException();
		}
		this.accessToken = accessToken;
		this.userId = userId;
		this.role = role;
	}

}
