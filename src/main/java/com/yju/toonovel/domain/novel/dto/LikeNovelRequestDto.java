package com.yju.toonovel.domain.novel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeNovelRequestDto {

	private Long userId;
	private Long novelId;

	@Builder
	public LikeNovelRequestDto(Long userId, Long novelId) {
		this.userId = userId;
		this.novelId = novelId;
	}
}
