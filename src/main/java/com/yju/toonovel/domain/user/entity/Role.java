package com.yju.toonovel.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	GUEST("ROLE_GUEST"),
	USER("ROLE_USER"),
	AUTHOR("ROLE_AUTHOR");

	private final String key;

}
