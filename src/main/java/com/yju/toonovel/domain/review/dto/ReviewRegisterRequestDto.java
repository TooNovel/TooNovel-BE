
package com.yju.toonovel.domain.review.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.yju.toonovel.domain.novel.entity.Novel;
import com.yju.toonovel.domain.review.entity.Review;
import com.yju.toonovel.domain.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "리뷰 작성 요청 DTO")
@Getter
@NoArgsConstructor
public class ReviewRegisterRequestDto {

	@Schema(description = "소설 ID")
	private Long novelId;
	@Schema(description = "리뷰 본문")
	@NotBlank
	@Length(max = 1000)
	private String reviewContent;
	@Schema(description = "평점")
	@NotBlank
	@Max(value = 5)
	@Min(value = 0)
	private int reviewGrade;

	@Builder
	public ReviewRegisterRequestDto(Long novelId, String reviewContent, int reviewGrade) {
		this.novelId = novelId;
		this.reviewContent = reviewContent;
		this.reviewGrade = reviewGrade;
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
