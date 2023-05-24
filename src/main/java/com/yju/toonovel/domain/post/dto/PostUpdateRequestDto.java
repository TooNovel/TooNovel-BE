package com.yju.toonovel.domain.post.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.yju.toonovel.domain.post.entity.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {

	@NotBlank
	@Length(max = 50)
	private String title;
	@NotBlank
	private String content;
	@NotBlank
	private Category category;

	@Builder
	public PostUpdateRequestDto(String title, String content, Category category) {
		this.title = title;
		this.content = content;
		this.category = category;
	}

}
