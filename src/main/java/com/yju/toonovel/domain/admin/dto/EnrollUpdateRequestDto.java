package com.yju.toonovel.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "작가 업데이트 요청 DTO")
@Getter
@NoArgsConstructor
public class EnrollUpdateRequestDto {

	@Schema(description = "작가 신청한 유저ID")
	private Long userId;
	@Schema(description = "작가 신청한 유저 닉네임")
	private String nickname;
	@Schema(description = "신청확인 테이블에 존재하는지 체크할 신청ID")
	private Long enrollId;

	@Builder
	public EnrollUpdateRequestDto(Long userId, String nickname, Long enrollId) {
		this.userId = userId;
		this.nickname = nickname;
		this.enrollId = enrollId;
	}
}
