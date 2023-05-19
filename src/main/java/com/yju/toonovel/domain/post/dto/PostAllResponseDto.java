package com.yju.toonovel.domain.post.dto;

import java.time.LocalDateTime;

import com.yju.toonovel.domain.post.entity.Category;
import com.yju.toonovel.domain.post.entity.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostAllResponseDto {
	private Long postId;
	private Category category;
	private String title;
	private String content;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private Long like;
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
