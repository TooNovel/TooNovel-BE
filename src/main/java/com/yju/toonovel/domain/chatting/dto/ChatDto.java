package com.yju.toonovel.domain.chatting.dto;

import com.yju.toonovel.domain.user.entity.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class ChatDto {
	private Long chatId;
	private String senderAccessToken;
	private String senderName;
	@Setter
	private Long senderId;
	@Setter
	private Role role;
	private String message;

	@Builder
	public ChatDto(Long chatId, String senderName, Long senderId, Role role, String message) {
		this.chatId = chatId;
		this.senderName = senderName;
		this.senderId = senderId;
		this.role = role;
		this.message = message;
	}

	public static ChatDto of(Long chatId, String senderName, Long senderId, Role role, String message) {
		// chatId = no offset 페이징용
		// senderName = 채팅방에 발신자 닉네임 표시
		// senderId = 수신자가 자신이 보낸 메세지인지 구분 (닉네임이 unique하면 필요 x)
		// role = 수신자(작가)가 자신이 보낸건지, 독자가 보낸건지 구분
		// message = 메시지 내용
		return ChatDto.builder()
			.chatId(chatId)
			.senderName(senderName)
			.senderId(senderId)
			.role(role)
			.message(message)
			.build();
	}
}
