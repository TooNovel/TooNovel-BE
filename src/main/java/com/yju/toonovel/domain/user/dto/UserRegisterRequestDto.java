package com.yju.toonovel.domain.user.dto;

import com.yju.toonovel.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDto {

	private String nickname;
	private String gender;
	private String birth;
	private String oauthId;

	public User dtoToEntity() {
		User user = User.builder()
			.nickname(nickname)
			.gender(gender)
			.birth(birth)
			.oauthId(oauthId)
			.build();

		return user;
	}
}
