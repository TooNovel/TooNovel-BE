
package com.yju.toonovel.domain.review.dto;

import java.time.LocalDateTime;

import com.yju.toonovel.domain.novel.entity.Novel;
import com.yju.toonovel.domain.review.entity.Review;
import com.yju.toonovel.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRegisterRequestDto {

	//리뷰 entity
	private Long reviewId;
	private String reviewContent;
	private int reviewGrade;
	private LocalDateTime createDate;

	//유저 entity
	private Long writerId;

	//작품 entity
	private Long novelId;

	@Builder
	public ReviewRegisterRequestDto(Long reviewId, String reviewContent, int reviewGrade,
		LocalDateTime createDate, Long writerId, Long novelId) {
		this.reviewId = reviewId;
		this.reviewContent = reviewContent;
		this.reviewGrade = reviewGrade;
		this.createDate = createDate;
		this.writerId = writerId;
		this.novelId = novelId;
	}

	// dtoToEntity 수정필요 - 코드내의 엔티티 생성 빌더를 없애고 매개변수로 dto를 받을지 회의 필요
	//현재는 흐름테스트를 위해 이 코드 사용
	public Review dtoToEntity(User user, Novel novel) {
		Review review = Review.builder()
			.reviewContent(reviewContent)
			.reviewGrade(reviewGrade)
			.writer(user)
			.novel(novel)
			.build();

		return review;
	}
}
