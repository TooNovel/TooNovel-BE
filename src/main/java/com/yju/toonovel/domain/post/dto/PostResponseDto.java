package com.yju.toonovel.domain.post.dto;

import java.time.LocalDateTime;

import com.yju.toonovel.domain.post.entity.Category;
import com.yju.toonovel.domain.post.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
	private Long postId;
	private String authorName;
	private Category categoryId;
	private String title;
	private String content;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private Long like;
	private Long dislike;
	private Long viewCount;

	public PostResponseDto(Post entity) {
		this.postId = entity.getPostId();
		this.authorName = "임시 닉네임";
		this.categoryId = entity.getCategoryId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.createdDate = entity.getCreatedDate();
		this.modifiedDate = entity.getModifiedDate();
		this.like = entity.getLike();
		this.dislike = entity.getDislike();
		this.viewCount = entity.getViewCount();
	}
}
