package com.yju.toonovel.global.security.jwt;

import com.yju.toonovel.global.security.jwt.exception.InvalidTokenException;

import lombok.ToString;

@ToString
public class JwtAuthentication {

	public final String accessToken;
	public final Long userId;

	public JwtAuthentication(String accessToken, Long userId) {
		if (accessToken.isEmpty() || accessToken.isBlank()) {
			throw new InvalidTokenException();
		}
		if (userId <= 0) {
			throw new InvalidTokenException();
		}
		this.accessToken = accessToken;
		this.userId = userId;
	}

}
