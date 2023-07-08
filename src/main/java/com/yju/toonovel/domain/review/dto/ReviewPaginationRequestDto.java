package com.yju.toonovel.domain.review.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.global.common.Sort;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "리뷰 조회 요청 DTO")
@Getter
public class ReviewPaginationRequestDto {

	@Schema(description = "page 번호")
	@PositiveOrZero
	private final Integer page;
	@Schema(description = "한 페이지안에 담길 리뷰의 수")
	@Positive
	private final Integer limit;
	@Schema(description = "장르")
	private final Genre genre;
	@Schema(description = "정렬 기준", defaultValue = "CREATED_DATE_DESC")
	private final Sort sort;

	@Builder
	public ReviewPaginationRequestDto(Integer page, Integer limit, Genre genre, Sort sort) {
		this.page = page == null ? 0 : page;
		this.genre = genre;
		this.limit = limit == null ? 10 : limit;
		this.sort = sort == null ? Sort.CREATED_DATE_DESC : sort;
	}
}
