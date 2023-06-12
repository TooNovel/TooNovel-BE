package com.yju.toonovel.domain.chatting.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WebSocketErrorResponse {

	private String roomId;
	private Long userId;
	private HttpStatus status;
	private String code;
	private String message;

	@Builder
	public WebSocketErrorResponse(String roomId, Long userId, HttpStatus status, String code, String message) {
		this.roomId = roomId;
		this.userId = userId;
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public static WebSocketErrorResponse of(
		String roomId,
		Long userId,
		HttpStatus status,
		String code,
		String message) {
		return WebSocketErrorResponse.builder()
			.roomId(roomId)
			.userId(userId)
			.status(status)
			.code(code)
			.message(message)
			.build();
	}
}
