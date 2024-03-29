package com.yju.toonovel.domain.post.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.yju.toonovel.domain.post.entity.Category;
import com.yju.toonovel.global.common.Sort;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "게시글 조회 요청 DTO")
@Getter
public class PostPaginationRequestDto {

	@Schema(description = "page 번호")
	@PositiveOrZero
	private final Integer page;
	@Schema(description = "게시글 카테고리")
	private final Category category;
	@Schema(description = "한 페이지안에 담길 게시글의 수")
	@Positive
	private final Integer limit;
	@Schema(description = "정렬 기준", defaultValue = "CREATED_DATE_DESC")
	private final Sort sort;

	@Builder
	private PostPaginationRequestDto(Integer page, Category category, Integer limit, Sort sort) {
		this.page = page == null ? 0 : page;
		this.category = category;
		this.limit = limit == null ? 10 : limit;
		this.sort = sort == null ? Sort.CREATED_DATE_DESC : sort;
	}

}
