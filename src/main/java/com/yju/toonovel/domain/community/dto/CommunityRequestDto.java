package com.yju.toonovel.domain.community.dto;

import com.yju.toonovel.domain.community.entity.Category;
import com.yju.toonovel.domain.community.entity.Community;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityRequestDto {
	private Long userId;
	private String title;
	private String content;
	private Category category;

	public Community toEntity() {
		return Community.builder()
			.userId(userId)
			.categoryId(category)
			.title(title)
			.content(content)
			.build();
	}
}
