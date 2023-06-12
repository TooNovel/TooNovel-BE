package com.yju.toonovel.domain.chatting.exception.websocket;

import org.springframework.http.HttpStatus;

import com.yju.toonovel.domain.chatting.exception.ChatRoomNotMatchUserException;

import lombok.Getter;

@Getter
public class ChatRoomNotMatchUserWebSocketException
	extends ChatRoomNotMatchUserException
	implements WebSocketExceptionInterface {
	private String roomId;
	private Long userId;
	private HttpStatus status;
	private String code;
	private String message;

	public ChatRoomNotMatchUserWebSocketException(Long userId, String roomId) {
		this.userId = userId;
		this.roomId = roomId;
		this.status = super.getErrorCode().getStatus();
		this.code = super.getErrorCode().getCode();
		this.message = super.getErrorCode().getMessage();
	}
}
