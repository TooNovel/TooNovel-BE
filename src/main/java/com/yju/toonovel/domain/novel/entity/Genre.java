package com.yju.toonovel.domain.novel.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Genre {
	FANTASY("판타지"),
	ROMANCE_FANTASY("로맨스판타지"),
	ROMANCE("로맨스"),
	MODERN_FANTASY("현대판타지"),
	WUXIA("무협"),
	MYSTERY("미스터리"),
	LIGHT_NOVEL("라이트노벨"),
	BL("BL");

	private final String key;

}
