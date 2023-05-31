package com.yju.toonovel.domain.novel.dto;

import com.yju.toonovel.domain.novel.entity.Platform;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PlatformResponseDto {

	@Schema(description = "플랫폼 ID")
	private Long platformId;
	@Schema(description = "플랫폼 이름")
	private String platformName;
	@Schema(description = "서비스 중인 url")
	private String url;

	public PlatformResponseDto(Platform platform, String url) {
		this.platformId = platform.getPlatformId();
		this.platformName = platform.getPlatformName();
		this.url = url;
	}

}
