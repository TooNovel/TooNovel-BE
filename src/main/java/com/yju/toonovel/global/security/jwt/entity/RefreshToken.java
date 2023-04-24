package com.yju.toonovel.global.security.jwt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class RefreshToken {

	@Id
	private long userId;

	@Column(nullable = false, unique = true)
	private String refreshToken;

	@Builder
	public RefreshToken(Long userId, String refreshToken) {
		this.userId = userId;
		this.refreshToken = refreshToken;
	}

	public void update(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
