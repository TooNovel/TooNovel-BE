package com.yju.toonovel.global.websocket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChatDto {
	private String senderAccessToken;
	private String senderName;
	private Long senderId;
	private String senderRole;
	private String message;

	@Builder
	public ChatDto(String senderAccessToken, String senderName, String message) {
		this.senderAccessToken = senderAccessToken;
		this.senderName = senderName;
		this.message = message;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public void setSenderRole(String senderRole) {
		this.senderRole = senderRole;
	}
}
