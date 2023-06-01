package com.yju.toonovel.domain.admin.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Schema(description = "작가 신청 리스트 조회 요청 DTO")
@Getter
@NoArgsConstructor
public class EnrollListResponseDto {

	@Schema(description = "작가 신청한 유저")
	private Long userId;
	@Schema(description = "신청확인 테이블에 존재하는지 체크할 신청번호")
	private Long enrollId;
	@Schema(description = "닉네임")
	@Length(max = 15)
	@NotBlank
	private String nickname;
	@Schema(description = "작가 승인 여부 정렬 기준")
	private Boolean isApproval;
	@Schema(description = "작가 신청 날짜")
	private LocalDateTime createdDate;

	@Builder
	public EnrollListResponseDto(Long userId, Long enrollId, String nickname,
		LocalDateTime createdDate, Boolean isApproval) {
		this.userId = userId;
		this.enrollId = enrollId;
		this.nickname = nickname;
		this.createdDate = createdDate;
		this.isApproval = isApproval;
	}

}
