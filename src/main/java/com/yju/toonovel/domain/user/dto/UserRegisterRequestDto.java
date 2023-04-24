package com.yju.toonovel.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegisterRequestDto {

	private String nickname;
	private String gender;
	private String birth;
	private String userId;

	@Builder
	public UserRegisterRequestDto(String nickname, String gender, String birth, String userId) {
		this.nickname = nickname;
		this.gender = gender;
		this.birth = birth;
		this.userId = userId;
	}

}
