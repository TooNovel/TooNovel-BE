package com.yju.toonovel.domain.chatting.dto;

import com.yju.toonovel.domain.user.entity.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyDto {
	private Long replyId;
	private String senderAccessToken;
	private String senderName;
	private Long senderId;
	private Role role;
	private String message;
	private Long chatId;
	private String userMessage;

	@Builder
	public ReplyDto(String senderName, Long senderId, Role role, String message, String userMessage) {
		this.senderName = senderName;
		this.senderId = senderId;
		this.role = role;
		this.message = message;
		this.userMessage = userMessage;
	}

	public static ReplyDto of(String senderName, Long senderId, Role role, String message, String userMessage) {
		return ReplyDto.builder()
			.senderName(senderName)
			.senderId(senderId)
			.role(role)
			.message(message)
			.userMessage(userMessage)
			.build();
	}
}
