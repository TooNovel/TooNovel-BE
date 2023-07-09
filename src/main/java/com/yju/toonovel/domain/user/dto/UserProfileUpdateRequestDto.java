package com.yju.toonovel.domain.user.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원 프로필 수정 요청 DTO")
@Getter
@NoArgsConstructor
public class UserProfileUpdateRequestDto {

	@Schema(description = "닉네임")
	@Length(max = 15)
	@NotBlank
	private String nickname;
	@Schema(description = "유저 프로필 사진")
	private String imageUrl;

}
