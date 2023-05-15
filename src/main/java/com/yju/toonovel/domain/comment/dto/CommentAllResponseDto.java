package com.yju.toonovel.domain.comment.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentAllResponseDto {

	//user
	private String imageUrl;
	private String nickname;

	//comment
	private Long commentId;
	private String commentContent;
	private LocalDateTime createdDate;
	private int commentLike;

	//likeComment
	private boolean isActived;

	@Builder
	public CommentAllResponseDto(String imageUrl, String nickname,
		Long commentId, String commentContent, LocalDateTime createdDate, int commentLike, boolean isActived) {

		this.imageUrl = imageUrl;
		this.nickname = nickname;
		this.commentId = commentId;
		this.commentContent = commentContent;
		this.createdDate = createdDate;
		this.commentLike = commentLike;
		this.isActived = isActived;
	}
}
