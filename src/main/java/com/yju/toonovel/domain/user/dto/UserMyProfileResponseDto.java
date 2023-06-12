package com.yju.toonovel.domain.user.dto;

import com.yju.toonovel.domain.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "유저 마이 프로필 조회 응답 DTO")
@Getter
public class UserMyProfileResponseDto {

	@Schema(description = "유저 ID")
	private Long userId;
	@Schema(description = "유저 닉네임")
	private String nickname;
	@Schema(description = "유저 프로필 사진")
	private String imageUrl;
	@Schema(description = "유저 성별")
	private String gender;
	@Schema(description = "유저 생년월일")
	private String birth;

	public UserMyProfileResponseDto(User user) {
		this.userId = user.getUserId();
		this.nickname = user.getNickname();
		this.imageUrl = user.getImageUrl();
		this.gender = user.getGender();
		this.birth = user.getBirth();

	}

}
