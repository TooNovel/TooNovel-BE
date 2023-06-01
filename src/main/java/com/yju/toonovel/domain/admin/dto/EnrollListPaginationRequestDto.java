package com.yju.toonovel.domain.admin.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.yju.toonovel.global.common.Sort;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "작가 신청 리스트 페이징 처리 요청 DTO")
@Getter
public class EnrollListPaginationRequestDto {

	@Schema(description = "page 번호")
	@PositiveOrZero
	private Integer page;
	@Schema(description = "한 페이지안에 담길 작가 신청 리스트의 수")
	@Positive
	private Integer limit;
	@Schema(description = "신청 승인 여부")
	private Boolean isApproval;
	@Schema(description = "정렬 기준", defaultValue = "CREATED_DATE_DESC")
	private Sort sort;

	@Builder
	public EnrollListPaginationRequestDto(Integer page, Integer limit, Boolean isApproval, Sort sort) {
		this.page = page == null ? 0 : page;
		this.isApproval = isApproval;
		this.limit = limit == null ? 10 : limit;
		this.sort = sort == null ? Sort.CREATED_DATE_DESC : sort;
	}
}
