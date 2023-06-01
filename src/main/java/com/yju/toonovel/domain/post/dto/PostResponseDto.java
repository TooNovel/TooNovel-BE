package com.yju.toonovel.domain.post.dto;

import java.time.LocalDateTime;

import com.yju.toonovel.domain.post.entity.Category;
import com.yju.toonovel.domain.post.entity.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "게시글 상세 조회 응답 DTO")
@Getter
@NoArgsConstructor
public class PostResponseDto {
	@Schema(description = "게시글 ID")
	private Long postId;
	@Schema(description = "게시글 카테고리")
	private Category category;
	@Schema(description = "게시글 제목")
	private String title;
	@Schema(description = "게시글 본문")
	private String content;
	@Schema(description = "게시글 작성일")
	private LocalDateTime createdDate;
	@Schema(description = "게시글 수정일")
	private LocalDateTime modifiedDate;
	@Schema(description = "게시글 좋아요 수")
	private Long like;
	@Schema(description = "게시글 조회수")
	private Long viewCount;
	@Schema(description = "유저 닉네임")
	private String nickname;

	public PostResponseDto(Post post) {
		this.postId = post.getPostId();
		this.category = post.getCategory();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.createdDate = post.getCreatedDate();
		this.modifiedDate = post.getModifiedDate();
		this.like = post.getLike();
		this.viewCount = post.getViewCount();
		this.nickname = post.getUser().getNickname();
	}
}
