package com.yju.toonovel.domain.comment.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "댓글 조회 응답 DTO")
@Getter
@NoArgsConstructor
public class CommentAllResponseDto {

	@Schema(description = "작성자 프로필 사진")
	private String imageUrl;
	@Schema(description = "작성자 닉네임")
	private String nickname;

	@Schema(description = "댓글 ID")
	private Long commentId;
	@Schema(description = "댓글 본문")
	private String commentContent;
	@Schema(description = "댓글 작성일")
	private LocalDateTime createdDate;
	@Schema(description = "댓글 좋아요 수")
	private int commentLike;

	@Schema(description = "댓글 좋아요 여부")
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
