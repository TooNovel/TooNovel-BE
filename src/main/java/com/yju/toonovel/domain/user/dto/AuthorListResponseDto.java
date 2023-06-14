package com.yju.toonovel.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "최신 작가 요청 DTO")
@Getter
@NoArgsConstructor
public class AuthorListResponseDto {
	@Schema(description = "작가 ID")
	private Long userId;

	@Schema(description = "작가의 닉네임")
	private String nickname;

	@Schema(description = "작가의 프로필 사진")
	private String imageUrl;
}
