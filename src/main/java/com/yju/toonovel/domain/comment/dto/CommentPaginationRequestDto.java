package com.yju.toonovel.domain.comment.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.yju.toonovel.global.common.Sort;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentPaginationRequestDto {

	@PositiveOrZero
	private Integer page;
	@Positive
	private Integer limit;
	private Sort sort;

	@Builder
	public CommentPaginationRequestDto(Integer page, Integer limit, Sort sort) {
		this.page = page == null ? 0 : page;
		this.limit = limit == null ? 10 : limit;
		this.sort = sort == null ? Sort.CREATED_DATE_DESC : sort;
	}
}
