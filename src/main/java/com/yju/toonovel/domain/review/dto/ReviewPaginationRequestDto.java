package com.yju.toonovel.domain.review.dto;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.global.common.Sort;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewPaginationRequestDto {

	private int page;
	private int limit;
	private Genre genre;
	private Sort sort;

	@Builder
	public ReviewPaginationRequestDto(int page, int limit, Genre genre, Sort sort) {
		this.page = page;
		this.genre = genre;
		this.limit = limit;
		this.sort = sort == null ? sort.CREATED_DATE_DESC : sort;
	}
}
