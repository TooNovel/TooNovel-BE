package com.yju.toonovel.domain.review.dto;

import java.time.LocalDateTime;

import com.yju.toonovel.domain.novel.entity.Genre;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "유저가 작성한 조회 응답 DTO")
@Getter
@NoArgsConstructor
public class ReviewByUserResponseDto {

	@Schema(description = "유저 닉네임")
	private String nickname;
	@Schema(description = "유저 프로필 사진")
	private String imageUrl;

	@Schema(description = "소설 번호")
	private Long novelId;
	@Schema(description = "소설 제목")
	private String title;
	@Schema(description = "소설 설명")
	private String description;
	@Schema(description = "작가 이름")
	private String author;
	@Schema(description = "장르")
	private Genre genre;
	@Schema(description = "소설 표지")
	private String image;

	@Schema(description = "리뷰 본문")
	private String reviewContent;
	@Schema(description = "리뷰 평점")
	private int reviewGrade;
	@Schema(description = "리뷰 좋아요 수")
	private int reviewLike;
	@Schema(description = "리뷰 작성일")
	private LocalDateTime createdDate;

	@Builder
	public ReviewByUserResponseDto(String image, Genre genre, String author, String description, String title,
		Long novelId, String reviewContent, int reviewGrade, LocalDateTime createdDate, int reviewLike,
		String nickname, String imageUrl) {
		this.image = image;
		this.genre = genre;
		this.author = author;
		this.description = description;
		this.title = title;
		this.novelId = novelId;
		this.reviewContent = reviewContent;
		this.reviewGrade = reviewGrade;
		this.createdDate = createdDate;
		this.reviewLike = reviewLike;
		this.nickname = nickname;
		this.imageUrl = imageUrl;
	}

}
