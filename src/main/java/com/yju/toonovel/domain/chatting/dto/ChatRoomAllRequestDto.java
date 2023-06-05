package com.yju.toonovel.domain.chatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ChatRoomAllRequestDto {

	private String chatRoomName;
	private Long userId;

	@Builder
	public ChatRoomAllRequestDto(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}
}
