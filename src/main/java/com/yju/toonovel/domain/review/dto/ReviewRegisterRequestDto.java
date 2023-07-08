
package com.yju.toonovel.domain.review.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

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
	private ReviewRegisterRequestDto(Long novelId, String reviewContent, int reviewGrade) {
		this.novelId = novelId;
		this.reviewContent = reviewContent;
		this.reviewGrade = reviewGrade;
	}
}
