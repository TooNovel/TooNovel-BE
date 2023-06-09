package com.yju.toonovel.domain.chatting.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(description = "채팅방 생성 요청 DTO")
@Getter
@ToString
@NoArgsConstructor
public class ChatRoomCreateRequestDto {
	@NotBlank
	@Length(max = 50)
	@Schema(description = "채팅방 이름")
	private String chatRoomName;
	@Schema(description = "채팅방을 생성한 유저의 userId")
	private Long userId;

	@Builder
	public ChatRoomCreateRequestDto(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}
}
