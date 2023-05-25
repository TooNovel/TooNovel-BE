package com.yju.toonovel.domain.post.dto;

import java.time.LocalDateTime;

import com.yju.toonovel.domain.post.entity.Category;
import com.yju.toonovel.domain.post.entity.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "게시글 조회 응답 DTO")
@Getter
@NoArgsConstructor
public class PostAllResponseDto {

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

	@Builder
	public PostAllResponseDto(Long postId, Category category,
		String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, Long like,
		Long viewCount) {

		this.postId = postId;
		this.category = category;
		this.title = title;
		this.content = content;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.like = like;
		this.viewCount = viewCount;
	}

	public PostAllResponseDto(Post post) {

		this.postId = post.getPostId();
		this.category = post.getCategory();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.createdDate = post.getCreatedDate();
		this.modifiedDate = post.getModifiedDate();
		this.like = post.getLike();
		this.viewCount = post.getViewCount();
	}

}
