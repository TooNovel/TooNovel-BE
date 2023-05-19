package com.yju.toonovel.domain.post.dto;

import com.yju.toonovel.domain.post.entity.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRegisterRequestDto {
	private String title;
	private String content;
	private Category category;

	@Builder
	public PostRegisterRequestDto(String title, String content, Category category) {
		this.title = title;
		this.content = content;
		this.category = category;
	}
}
