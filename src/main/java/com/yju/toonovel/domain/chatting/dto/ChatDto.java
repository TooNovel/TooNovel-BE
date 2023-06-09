package com.yju.toonovel.domain.chatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class ChatDto {
	private Long chatId;
	private String senderName;
	private Long senderId;
	private boolean isCreator;
	private String message;

	@Builder
	public ChatDto(Long chatId, String senderName, Long senderId, boolean isCreator, String message) {
		this.chatId = chatId;
		this.senderName = senderName;
		this.senderId = senderId;
		this.isCreator = isCreator;
		this.message = message;
	}

	public static ChatDto of(Long chatId, String senderName, Long senderId, boolean isCreator, String message) {
		// chatId = no offset 페이징용, Reply시 식별용
		// senderName = 채팅방에 발신자 닉네임 표시
		// senderId = 수신자가 자신이 보낸 메세지인지 구분
		// isCreator = 해당 채팅방을 생성한 작가 보낸건지 구분
		// message = 메시지 내용
		return ChatDto.builder()
			.chatId(chatId)
			.senderName(senderName)
			.senderId(senderId)
			.isCreator(isCreator)
			.message(message)
			.build();
	}
}
