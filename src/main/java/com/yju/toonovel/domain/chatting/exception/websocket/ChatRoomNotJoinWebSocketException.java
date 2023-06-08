package com.yju.toonovel.domain.chatting.exception.websocket;

import org.springframework.http.HttpStatus;

import com.yju.toonovel.domain.chatting.exception.ChatRoomNotJoinException;

import lombok.Getter;
@Getter
public class ChatRoomNotJoinWebSocketException extends ChatRoomNotJoinException {

	private String roomId;
	private Long userId;
	private HttpStatus status;
	private String code;
	private String message;

	public ChatRoomNotJoinWebSocketException(Long userId, String roomId) {
		this.userId = userId;
		this.roomId = roomId;
		this.status = super.getErrorCode().getStatus();
		this.code = super.getErrorCode().getCode();
		this.message = super.getErrorCode().getMessage();
	}
}
