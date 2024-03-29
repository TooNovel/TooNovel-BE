package com.yju.toonovel.domain.user.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "작가 신청 요청 DTO")
@Getter
@NoArgsConstructor
public class AuthorRegisterRequestDto {

	@Schema(description = "닉네임")
	@Length(max = 15)
	@NotBlank
	private String nickname;

}
