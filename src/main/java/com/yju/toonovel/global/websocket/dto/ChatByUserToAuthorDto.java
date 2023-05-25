package com.yju.toonovel.global.websocket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChatByUserToAuthorDto {
	private String senderAccessToken;
	private Long senderId;
	private String senderName; // DB 쿼리 작업을 뺀 관계로 임시로 추가. 추후 필요 여부 검토 필요
	private String receiverId;
	private String message;

	@Builder
	public ChatByUserToAuthorDto(String senderAccessToken, String senderName, String receiverId, String message) {
		this.senderAccessToken = senderAccessToken;
		this.senderName = senderName;
		this.receiverId = receiverId;
		this.message = message;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
}
