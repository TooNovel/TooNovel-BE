package com.yju.toonovel.domain.novel.dto;

import com.yju.toonovel.domain.novel.entity.Platform;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PlatformResponseDto {
	private Long platformId;
	private String platformName;

	public PlatformResponseDto(Platform platform) {
		this.platformId = platform.getPlatformId();
		this.platformName = platform.getPlatformName();
	}

}
