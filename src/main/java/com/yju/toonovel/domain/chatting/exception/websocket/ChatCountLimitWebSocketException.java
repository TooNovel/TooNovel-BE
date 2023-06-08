package com.yju.toonovel.domain.chatting.exception.websocket;

import org.springframework.http.HttpStatus;

import com.yju.toonovel.domain.chatting.exception.ChatCountLimitException;

import lombok.Getter;

@Getter
public class ChatCountLimitWebSocketException extends ChatCountLimitException implements WebSocketExceptionInterface {

	private String roomId;
	private Long userId;
	private HttpStatus status;
	private String code;
	private String message;

	public ChatCountLimitWebSocketException(Long userId, String roomId) {
		this.userId = userId;
		this.roomId = roomId;
		this.status = super.getErrorCode().getStatus();
		this.code = super.getErrorCode().getCode();
		this.message = super.getErrorCode().getMessage();
	}
}
