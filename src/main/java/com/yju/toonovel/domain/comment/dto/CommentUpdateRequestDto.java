package com.yju.toonovel.domain.comment.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {

	@NotBlank
	@Length(max = 500)
	private String commentContent;

	@Builder
	public CommentUpdateRequestDto(String commentContent) {
		this.commentContent = commentContent;
	}
}
