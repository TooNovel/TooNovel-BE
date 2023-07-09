package com.yju.toonovel.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(description = "작가 리스트 조회 요청 DTO")
@Getter
@RequiredArgsConstructor
public class AuthorListPaginationRequestDto {

	@Schema(description = "이전에 받은 마지막 enroll ID")
	private final Long enrollId;

	@Schema(description = "작가 이름")
	private final String nickname;
}
