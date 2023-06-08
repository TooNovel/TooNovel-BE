package com.yju.toonovel.domain.chatting.exception.websocket;

import org.springframework.http.HttpStatus;

public interface WebSocketExceptionInterface {
	String getRoomId();

	Long getUserId();

	HttpStatus getStatus();

	String getCode();

	String getMessage();
}
