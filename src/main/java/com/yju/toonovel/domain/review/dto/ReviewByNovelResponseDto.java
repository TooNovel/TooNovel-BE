package com.yju.toonovel.domain.review.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "특정 소설에 속한 리뷰 조회 응답 DTO")
@Getter
@NoArgsConstructor
public class ReviewByNovelResponseDto {

	@Schema(description = "작성자 ID")
	private Long userId;
	@Schema(description = "작성자 닉네임")
	private String nickname;
	@Schema(description = "작성자 프로필 사진")
	private String imageUrl;
	@Schema(description = "리뷰 ID")
	private Long reviewId;
	@Schema(description = "리뷰 작성일")
	private LocalDateTime createdDate;
	@Schema(description = "리뷰 평점")
	private Integer reviewGrade;
	@Schema(description = "리뷰 좋아요 수")
	private Integer reviewLike;
	@Schema(description = "리뷰 본문")
	private String reviewContent;
}
