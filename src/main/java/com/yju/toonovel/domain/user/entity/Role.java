package com.yju.toonovel.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	GUEST("ROLE_READER", "독자"),
	USER("ROLE_AUTHOR", "작가");

	private final String key;
	private final String title;
}
