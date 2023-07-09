package com.yju.toonovel.domain.user.dto;

import com.yju.toonovel.domain.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "유저 프로필 조회 응답 DTO")
@Getter
public class UserProfileResponseDto {

	@Schema(description = "유저 ID")
	private final Long userId;
	@Schema(description = "유저 닉네임")
	private final String nickname;
	@Schema(description = "유저 프로필 사진")
	private final String imageUrl;

	private UserProfileResponseDto(Long userId, String nickname, String imageUrl) {
		this.userId = userId;
		this.nickname = nickname;
		this.imageUrl = imageUrl;
	}

	public static UserProfileResponseDto from(User user) {
		return new UserProfileResponseDto(
			user.getUserId(),
			user.getNickname(),
			user.getImageUrl()
		);

	}

}
