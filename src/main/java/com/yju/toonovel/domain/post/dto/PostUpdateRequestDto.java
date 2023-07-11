package com.yju.toonovel.domain.post.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.yju.toonovel.domain.post.entity.Category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "게시글 수정 요청 DTO")
@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {

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

}
