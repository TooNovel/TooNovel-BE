package com.yju.toonovel.domain.chatting.dto;

import com.yju.toonovel.domain.user.entity.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class ChatDto {
	private String senderAccessToken;
	private String senderName;
	@Setter
	private Long senderId;
	@Setter
	private Role role;
	private String message;

	@Builder
	public ChatDto(String senderAccessToken, String senderName, String message) {
		this.senderAccessToken = senderAccessToken;
		this.senderName = senderName;
		this.message = message;
	}
}
