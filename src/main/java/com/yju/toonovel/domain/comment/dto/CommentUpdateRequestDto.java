package com.yju.toonovel.domain.comment.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "댓글 수정 요청 DTO")
@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {

	@NotBlank
	@Length(max = 500)
	@Schema(description = "댓글 본문")
	private String commentContent;

	@Builder
	public CommentUpdateRequestDto(String commentContent) {
		this.commentContent = commentContent;
	}
}
