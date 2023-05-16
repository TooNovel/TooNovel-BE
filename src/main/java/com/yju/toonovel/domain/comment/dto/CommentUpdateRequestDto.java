package com.yju.toonovel.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {

	private Long commentId;
	private Long postId;
	private String commentContent;

	@Builder
	public CommentUpdateRequestDto(Long commentId, Long postId, String commentContent) {
		this.commentId = commentId;
		this.postId = postId;
		this.commentContent = commentContent;
	}
}
