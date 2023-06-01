package com.yju.toonovel.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(description = "게시글 좋아요 여부 체크 요청 DTO")
@Getter
@RequiredArgsConstructor
public class UserLikeCheckPostResponseDto {

	@Schema(description = "좋아요 여부")
	private final boolean like;

	public static UserLikeCheckPostResponseDto from(boolean isActived) {
		return new UserLikeCheckPostResponseDto(isActived);
	}
}
