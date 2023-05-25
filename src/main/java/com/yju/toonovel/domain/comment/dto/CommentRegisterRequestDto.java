package com.yju.toonovel.domain.comment.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "댓글 작성 요청 DTO")
@Getter
@NoArgsConstructor
public class CommentRegisterRequestDto {

	@Schema(description = "게시글 번호")
	private Long postId;

	@NotBlank
	@Length(max = 500)
	@Schema(description = "댓글 본문")
	private String commentContent;

	@Builder
	public CommentRegisterRequestDto(Long postId, String commentContent) {
		this.postId = postId;
		this.commentContent = commentContent;
	}
}
