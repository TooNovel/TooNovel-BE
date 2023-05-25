package com.yju.toonovel.domain.post.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.yju.toonovel.domain.post.entity.Category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "게시글 작성 요청 DTO")
@Getter
@NoArgsConstructor
public class PostRegisterRequestDto {

	@Schema(description = "게시글 제목")
	@NotBlank
	@Length(max = 50)
	private String title;
	@Schema(description = "게시글 본문")
	@NotBlank
	private String content;
	@Schema(description = "게시글 카테고리")
	@NotBlank
	private Category category;

	@Builder
	public PostRegisterRequestDto(String title, String content, Category category) {
		this.title = title;
		this.content = content;
		this.category = category;
	}
}
