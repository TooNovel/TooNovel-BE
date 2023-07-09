
package com.yju.toonovel.domain.review.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@NotNull
	@Max(value = 5)
	@Min(value = 0)
	private int reviewGrade;
}
