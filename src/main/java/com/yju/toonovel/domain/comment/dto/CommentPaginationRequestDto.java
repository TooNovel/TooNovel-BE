package com.yju.toonovel.domain.comment.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.yju.toonovel.global.common.Sort;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "댓글 조회 요청 DTO")
@Getter
public class CommentPaginationRequestDto {

	@PositiveOrZero
	@Schema(description = "page 번호")
	private Integer page;
	@Positive
	@Schema(description = "한 페이지안에 담길 댓글의 수")
	private Integer limit;
	@Schema(description = "정렬 기준", defaultValue = "CREATED_DATE_DESC")
	private Sort sort;

	@Builder
	public CommentPaginationRequestDto(Integer page, Integer limit, Sort sort) {
		this.page = page == null ? 0 : page;
		this.limit = limit == null ? 10 : limit;
		this.sort = sort == null ? Sort.CREATED_DATE_DESC : sort;
	}
}
