package com.yju.toonovel.domain.novel.dto;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.domain.novel.entity.Novel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "소설 조회 응답 DTO")
@Getter
public class NovelPaginationResponseDto {

	@Schema(description = "소설 ID")
	private Long novelId;
	@Schema(description = "소설 제목")
	private String title;
	@Schema(description = "작가 이름")
	private String author;
	@Schema(description = "장르")
	private Genre genre;
	@Schema(description = "소설 평점")
	private Double grade;
	@Schema(description = "소설 표지")
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
