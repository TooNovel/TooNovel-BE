package com.yju.toonovel.domain.user.dto;

import com.yju.toonovel.domain.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "유저 프로필 조회 응답 DTO")
@Getter
public class UserProfileResponseDto {

	@Schema(description = "유저 ID")
	private Long userId;
	@Schema(description = "유저 닉네임")
	private String nickname;
	@Schema(description = "유저 프로필 사진")
	private String imageUrl;

	public UserProfileResponseDto(User user) {
		this.userId = user.getUserId();
		this.nickname = user.getNickname();
		this.imageUrl = user.getImageUrl();

	}

}
