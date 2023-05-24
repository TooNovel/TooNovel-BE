package com.yju.toonovel.domain.comment.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {

	private Long commentId;
	@NotBlank
	@Length(max = 500)
	private String commentContent;

	@Builder
	public CommentUpdateRequestDto(Long commentId, String commentContent) {
		this.commentId = commentId;
		this.commentContent = commentContent;
	}
}
