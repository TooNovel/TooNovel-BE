package com.yju.toonovel.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRegisterRequestDto {

	private Long postId;
	private String commentContent;

	@Builder
	public CommentRegisterRequestDto(Long postId, String commentContent) {
		this.postId = postId;
		this.commentContent = commentContent;
	}
}
