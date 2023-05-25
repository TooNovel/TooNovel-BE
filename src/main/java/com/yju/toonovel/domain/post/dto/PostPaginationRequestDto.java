package com.yju.toonovel.domain.post.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.yju.toonovel.domain.post.entity.Category;
import com.yju.toonovel.global.common.Sort;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostPaginationRequestDto {

	@PositiveOrZero
	private Integer page;
	private Category category;
	@Positive
	private Integer limit;
	private Sort sort;

	@Builder
	public PostPaginationRequestDto(Integer page, Category category, Integer limit, Sort sort) {
		this.page = page == null ? 0 : page;
		this.category = category;
		this.limit = limit == null ? 10 : limit;
		this.sort = sort == null ? Sort.CREATED_DATE_DESC : sort;
	}

}
