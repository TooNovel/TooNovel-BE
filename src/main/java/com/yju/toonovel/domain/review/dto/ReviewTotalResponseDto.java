package com.yju.toonovel.domain.review.dto;

import java.time.LocalDateTime;

import com.yju.toonovel.domain.review.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewTotalResponseDto {

	//유저 entity
	private String nickname;

	//리뷰 entity
	private String reviewContent;
	private int workGrade;
	private int reviewLike;
	private int reviewDisLike;

	//작성 날짜
	private String create_date;
}
