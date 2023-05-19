package com.yju.toonovel.domain.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
	FREE("자유"),
	NOVEL("소설"),
	ASK("질문"),
	SUGGEST("건의"),
	PROMOTE("홍보");

	private final String key;
}
