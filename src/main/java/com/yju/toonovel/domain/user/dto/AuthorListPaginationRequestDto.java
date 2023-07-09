package com.yju.toonovel.domain.user.dto;

import com.yju.toonovel.global.common.Sort;

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

	@Schema(description = "한 페이지 안에 담길 작가의 수", defaultValue = "30")
	private final Integer limit = 30;

	@Schema(description = "정렬 기준", defaultValue = "CREATED_DATE_DESC")
	private final Sort sort = Sort.CREATED_DATE_DESC;
}
