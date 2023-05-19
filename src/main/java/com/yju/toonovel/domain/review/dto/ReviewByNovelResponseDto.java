package com.yju.toonovel.domain.review.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewByNovelResponseDto {

	private String nickname;
	private String imageUrl;
	private LocalDateTime createdDate;

	private Integer reviewGrade;
	private Integer reviewLike;
	private String reviewContent;

	private Long userId;
	private Long reviewId;

	@Builder
	public ReviewByNovelResponseDto(String nickname, String imageUrl, LocalDateTime createdDate,
		Integer reviewGrade, Integer reviewLike, String reviewContent, Long userId, Long reviewId) {
		this.nickname = nickname;
		this.imageUrl = imageUrl;
		this.createdDate = createdDate;
		this.reviewGrade = reviewGrade;
		this.reviewLike = reviewLike;
		this.reviewContent = reviewContent;
		this.userId = userId;
		this.reviewId = reviewId;
	}
}
