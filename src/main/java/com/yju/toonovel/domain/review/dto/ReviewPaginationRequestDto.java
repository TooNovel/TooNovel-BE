package com.yju.toonovel.domain.review.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.global.common.Sort;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewPaginationRequestDto {

	@PositiveOrZero
	private Integer page;
	@Positive
	private Integer limit;
	private Genre genre;
	private Sort sort;

	@Builder
	public ReviewPaginationRequestDto(Integer page, Integer limit, Genre genre, Sort sort) {
		this.page = page == null ? 0 : page;
		this.genre = genre;
		this.limit = limit == null ? 10 : limit;
		this.sort = sort == null ? Sort.CREATED_DATE_DESC : sort;
	}
}
