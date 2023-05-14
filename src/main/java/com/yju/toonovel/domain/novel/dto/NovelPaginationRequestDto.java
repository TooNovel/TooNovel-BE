package com.yju.toonovel.domain.novel.dto;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.global.common.Sort;

import lombok.Getter;

@Getter
public class NovelPaginationRequestDto {

	private Long novelId;
	private String title;
	private String author;
	private Genre genre;
	private Sort sort;

	public NovelPaginationRequestDto(Long novelId, String title, String author, Genre genre, Sort sort) {
		this.novelId = novelId;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.sort = sort == null ? Sort.CREATED_DATE_DESC : sort;
	}

}
