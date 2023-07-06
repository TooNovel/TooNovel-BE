package com.yju.toonovel.domain.novel.dto;

import com.yju.toonovel.domain.novel.entity.Platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PlatformResponseDto {

	@Schema(description = "플랫폼 ID")
	private final Long platformId;
	@Schema(description = "플랫폼 이름")
	private final String platformName;
	@Schema(description = "서비스 중인 url")
	private final String url;

	private PlatformResponseDto(Platform platform, String url) {
		this.platformId = platform.getPlatformId();
		this.platformName = platform.getPlatformName();
		this.url = url;
	}

	public static PlatformResponseDto from(Platform platform, String url) {
		return new PlatformResponseDto(platform, url);
	}

}
