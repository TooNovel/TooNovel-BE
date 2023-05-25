package com.yju.toonovel.domain.novel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(description = "좋아요 여부 체크 요청 DTO")
@Getter
@RequiredArgsConstructor
public class UserLikeCheckResponseDto {

	@Schema(description = "좋아요 여부")
	private final boolean like;

	public static UserLikeCheckResponseDto from(boolean isActived) {
		return new UserLikeCheckResponseDto(isActived);
	}
}
