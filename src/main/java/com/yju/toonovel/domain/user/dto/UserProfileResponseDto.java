package com.yju.toonovel.domain.user.dto;

import com.yju.toonovel.domain.user.entity.User;

import lombok.Getter;

@Getter
public class UserProfileResponseDto {
	private Long userId;
	private String nickname;
	private String imageUrl;

	public UserProfileResponseDto(User user) {
		this.userId = user.getUserId();
		this.nickname = user.getNickname();
		this.imageUrl = user.getImageUrl();

	}

}
