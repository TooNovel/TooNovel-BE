package com.yju.toonovel.domain.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewLikeRegisterDto {

	private Long userId;
	private Long reviewId;

	@Builder
	public ReviewLikeRegisterDto(Long userId, Long reviewId) {
		this.userId = userId;
		this.reviewId = reviewId;
	}
}
