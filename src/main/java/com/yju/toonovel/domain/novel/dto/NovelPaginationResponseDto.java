package com.yju.toonovel.domain.novel.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.yju.toonovel.domain.novel.entity.Genre;
import com.yju.toonovel.domain.novel.entity.Novel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "소설 조회 응답 DTO")
@Getter
public class NovelPaginationResponseDto {

	@Schema(description = "소설 ID")
	private final Long novelId;
	@Schema(description = "소설 제목")
	private final String title;
	@Schema(description = "작가 이름")
	private final String author;
	@Schema(description = "장르")
	private final Genre genre;
	@Schema(description = "소설 평점")
	private final double grade;
	@Schema(description = "리뷰 수")
	private final Long reviewCount;
	@Schema(description = "좋아요 수")
	private final Long likeCount;
	@Schema(description = "소설 추가 일자")
	private final String createdDate;
	@Schema(description = "소설 표지")
	private final String image;

	@Builder
	private NovelPaginationResponseDto(Long novelId, String title, String author, Genre genre, Double grade,
		Long reviewCount, Long likeCount, LocalDateTime createdDate, String image) {
		this.novelId = novelId;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.grade = grade;
		this.reviewCount = reviewCount;
		this.likeCount = likeCount;
		this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.image = image;
	}

	public static NovelPaginationResponseDto from(Novel novel) {
		return new NovelPaginationResponseDto(
			novel.getNovelId(),
			novel.getTitle(),
			novel.getAuthor(),
			novel.getGenre(),
			novel.getGrade(),
			novel.getReviewCount(),
			novel.getLikeCount(),
			novel.getCreatedDate(),
			novel.getImage()
		);
	}

}
