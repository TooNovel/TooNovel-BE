package com.yju.toonovel.domain.chatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ChatRoomResponseDto {
	private Long chatRoomId;
	private String chatRoomName;
	private String nickname; // 채팅방을 생성한 작가의 닉네임

	@Builder
	public ChatRoomResponseDto(Long chatRoomId, String chatRoomName, String nickname) {
		this.chatRoomId = chatRoomId;
		this.chatRoomName = chatRoomName;
		this.nickname = nickname;
	}
}
