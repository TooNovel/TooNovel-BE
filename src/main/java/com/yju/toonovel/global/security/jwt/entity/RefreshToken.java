package com.yju.toonovel.global.security.jwt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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

	public RefreshToken(String refreshToken, Long userId) {
		this.refreshToken = refreshToken;
		this.userId = userId;
	}
}
