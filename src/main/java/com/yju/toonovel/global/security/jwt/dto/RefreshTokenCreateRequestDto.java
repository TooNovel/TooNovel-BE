package com.yju.toonovel.global.security.jwt.dto;

import com.yju.toonovel.global.security.jwt.entity.RefreshToken;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenCreateRequestDto {
	private long userId;
	private String refreshToken;

	@Builder
	public RefreshTokenCreateRequestDto(long userId, String refreshToken) {
		this.userId = userId;
		this.refreshToken = refreshToken;
	}

	public RefreshToken toEntity() {
		return RefreshToken.builder()
			.userId(userId)
			.refreshToken(refreshToken)
			.build();
	}
}
