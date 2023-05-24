package com.yju.toonovel.domain.comment.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRegisterRequestDto {

	@NotBlank
	@Length(max = 500)
	private String commentContent;

	@Builder
	public CommentRegisterRequestDto(String commentContent) {
		this.commentContent = commentContent;
	}
}
