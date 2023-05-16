package com.yju.toonovel.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegisterRequestDto {

	@Length(max = 15)
	@NotBlank
	private String nickname;
	@NotBlank
	private String gender;
	@NotBlank
	@Pattern(regexp = "(19|20)\\d{2}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])")
	private String birth;


	@Builder
	public UserRegisterRequestDto(String nickname, String gender, String birth) {
		this.nickname = nickname;
		this.gender = gender;
		this.birth = birth;
	}

}
