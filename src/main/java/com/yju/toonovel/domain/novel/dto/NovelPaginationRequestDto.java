package com.yju.toonovel.domain.novel.dto;

import com.yju.toonovel.domain.novel.entity.Genre;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NovelPaginationRequestDto {

	private Long novelId;
	private String title;
	private String author;
	private Genre genre;

	public NovelPaginationRequestDto(Long novelId, String title, String author, Genre genre) {
		this.novelId = novelId;
		this.title = title;
		this.author = author;
		this.genre = genre;
	}

}
