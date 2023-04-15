package com.yju.toonovel.domain.post.dto;

import com.yju.toonovel.domain.post.entity.Category;
import com.yju.toonovel.domain.post.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
	private Long postId;
	private Long userId;
	private String title;
	private String content;
	private Category category;

	public Post toEntity() {
		return Post.builder()
			.userId(userId)
			.categoryId(category)
			.title(title)
			.content(content)
			.build();
	}
}
