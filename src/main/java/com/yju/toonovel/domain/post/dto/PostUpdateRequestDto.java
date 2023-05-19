package com.yju.toonovel.domain.post.dto;

import com.yju.toonovel.domain.post.entity.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {

	private Long postId;
	private String title;
	private String content;
	private Category category;

	@Builder
	public PostUpdateRequestDto(Long postId, String title, String content, Category category) {
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.category = category;
	}

}
