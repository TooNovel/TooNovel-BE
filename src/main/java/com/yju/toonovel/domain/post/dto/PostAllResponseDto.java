package com.yju.toonovel.domain.post.dto;

import java.time.LocalDateTime;

import com.yju.toonovel.domain.post.entity.Category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "게시글 전체 조회 응답 DTO")
@Getter
@NoArgsConstructor
public class PostAllResponseDto {

	@Schema(description = "게시글 ID")
	private Long postId;
	@Schema(description = "게시글 카테고리")
	private Category category;
	@Schema(description = "게시글 제목")
	private String title;
	@Schema(description = "게시글 작성일")
	private LocalDateTime createdDate;
	@Schema(description = "게시글 조회수")
	private Long viewCount;
	@Schema(description = "유저 닉네임")
	private String nickname;
	@Schema(description = "댓글 수")
	private long commentCount;

	@Builder
	public PostAllResponseDto(Long postId, Category category,
		String title, LocalDateTime createdDate,
		Long viewCount, String nickname, long commentCount) {
		this.postId = postId;
		this.category = category;
		this.title = title;
		this.createdDate = createdDate;
		this.viewCount = viewCount;
		this.nickname = nickname;
		this.commentCount = commentCount;
	}

}
