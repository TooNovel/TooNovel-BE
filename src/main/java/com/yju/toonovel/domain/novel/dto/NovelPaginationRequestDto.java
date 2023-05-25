package com.yju.toonovel.domain.novel.dto;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.global.common.Sort;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "소설 조회 요청 DTO")
@Getter
public class NovelPaginationRequestDto {

	@Schema(description = "이전에 받은 마지막 소설 ID")
	private Long novelId;
	@Schema(description = "소설 제목")
	private String title;
	@Schema(description = "작가 이름")
	private String author;
	@Schema(description = "장르")
	private Genre genre;
	@Schema(description = "정렬 기준", defaultValue = "CREATED_DATE_DESC")
	private Sort sort;

	public NovelPaginationRequestDto(Long novelId, String title, String author, Genre genre, Sort sort) {
		this.novelId = novelId;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.sort = sort == null ? Sort.CREATED_DATE_DESC : sort;
	}

}
