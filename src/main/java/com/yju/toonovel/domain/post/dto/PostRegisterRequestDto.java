package com.yju.toonovel.domain.post.dto;

import com.yju.toonovel.domain.post.entity.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRegisterRequestDto {
	private Long postId;
	private Long userId;
	private String title;
	private String content;
	private Category category;

	@Builder
	public PostRegisterRequestDto(Long postId, Long userId, String title, String content, Category category) {
		this.postId = postId;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.category = category;
	}
}
