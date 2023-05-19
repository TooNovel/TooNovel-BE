package com.yju.toonovel.global.security.jwt.dto;

public class TokenReIssueResponseDto {

	private String accessToken;
	private String refreshToken;

	public TokenReIssueResponseDto(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
}
