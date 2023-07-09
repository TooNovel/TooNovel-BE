package com.yju.toonovel.domain.chatting.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Schema(description = "채팅방 조회 응답 DTO")
@Getter
@ToString
public class ChatRoomResponseDto {
	@Schema(description = "채팅방 번호")
	private final Long chatRoomId;
	@Schema(description = "채팅방 이름")
	private final String chatRoomName;
	@Schema(description = "채팅방을 생성한 작가의 닉네임")
	private final String nickname;
	@Schema(description = "작가의 userId")
	private final Long userId;

	public ChatRoomResponseDto(Long chatRoomId, String chatRoomName, String nickname, Long userId) {
		this.chatRoomId = chatRoomId;
		this.chatRoomName = chatRoomName;
		this.nickname = nickname;
		this.userId = userId;
	}
}
