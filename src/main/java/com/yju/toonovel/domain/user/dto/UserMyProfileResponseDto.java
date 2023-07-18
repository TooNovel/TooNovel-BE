package com.yju.toonovel.domain.user.dto;

import com.yju.toonovel.domain.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "유저 마이 프로필 조회 응답 DTO")
@Getter
public class UserMyProfileResponseDto {

	@Schema(description = "유저 ID")
	private final Long userId;
	@Schema(description = "유저 닉네임")
	private final String nickname;
	@Schema(description = "유저 프로필 사진")
	private final String imageUrl;
	@Schema(description = "유저 성별")
	private final String gender;
	@Schema(description = "유저 생년월일")
	private final String birth;

	private UserMyProfileResponseDto(Long userId, String nickname, String imageUrl, String gender, String birth) {
		this.userId = userId;
		this.nickname = nickname;
		this.imageUrl = imageUrl;
		this.gender = gender;
		this.birth = birth;
	}

	public static UserMyProfileResponseDto from(User user) {
		return new UserMyProfileResponseDto(
			user.getUserId(),
			user.getNickname(),
			user.getImageUrl(),
			user.getGender(),
			user.getBirth()
		);

	}

}
