package com.yju.toonovel.domain.novel.dto;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.domain.novel.entity.Novel;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NovelPaginationResponseDto {

	private Long novelId;
	private String title;
	private String author;
	private Genre genre;
	private Double grade;
	private String image;

	@Builder
	public NovelPaginationResponseDto(Long novelId, String title, String author, Genre genre, Double grade,
		String image) {
		this.novelId = novelId;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.grade = grade;
		this.image = image;
	}

	public static NovelPaginationResponseDto from(Novel novel) {
		return new NovelPaginationResponseDto(
			novel.getNovelId(),
			novel.getTitle(),
			novel.getAuthor(),
			novel.getGenre(),
			novel.getGrade(),
			novel.getImage()
		);
	}

}
