package com.yju.toonovel.domain.review.dto;

import java.time.LocalDateTime;

//리뷰조회 화면에 보여질 내용
public interface AllReviewInWorkResponseDto {

	String getNickname();

	String getImageUrl();

	LocalDateTime getCreatedDate();

	String getReviewContent();

	Integer getReviewGrade();

	Integer getReviewLike();

	Long getUserId();

	Boolean getIsActived();

}
