package com.yju.toonovel.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원 정보 수정 요청 DTO")
@Getter
@NoArgsConstructor
public class UserRegisterRequestDto {

	@Schema(description = "닉네임")
	@Length(max = 15)
	@NotBlank
	private String nickname;
	@Schema(description = "성별")
	@NotBlank
	private String gender;
	@Schema(description = "생년월일")
	@NotBlank
	@Pattern(regexp = "(19|20)\\d{2}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])")
	private String birth;

}
