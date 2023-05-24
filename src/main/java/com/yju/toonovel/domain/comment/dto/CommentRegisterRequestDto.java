package com.yju.toonovel.domain.comment.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRegisterRequestDto {

	private Long postId;
	@NotBlank
	@Length(max = 500)
	private String commentContent;

	@Builder
	public CommentRegisterRequestDto(Long postId, String commentContent) {
		this.postId = postId;
		this.commentContent = commentContent;
	}
}
