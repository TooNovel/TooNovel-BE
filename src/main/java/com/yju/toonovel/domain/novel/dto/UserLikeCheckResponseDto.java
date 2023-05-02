package com.yju.toonovel.domain.novel.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLikeCheckResponseDto {
	private final boolean like;

	public static UserLikeCheckResponseDto from(boolean isActived) {
		return new UserLikeCheckResponseDto(isActived);
	}
}
