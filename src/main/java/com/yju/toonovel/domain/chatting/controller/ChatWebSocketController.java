package com.yju.toonovel.domain.chatting.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.chatting.dto.ChatDto;
import com.yju.toonovel.domain.chatting.exception.WebSocketErrorResponse;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatCountLimitWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatRoomNotFoundWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatRoomNotJoinWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.UserNotFoundWebSocketException;
import com.yju.toonovel.domain.chatting.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class ChatWebSocketController {

	private final SimpMessagingTemplate messagingTemplate;
	private final ChatService chatService;

	@MessageMapping("/chat/{roomId}")
	public void chatToReader(@Payload ChatDto dto, @DestinationVariable String roomId) {
		chatService.authenticationAndSaveChat(dto, roomId);
		messagingTemplate.convertAndSend("/chat/" + roomId, dto);
	}

	@MessageExceptionHandler
	public void handleException(UserNotFoundWebSocketException exception) {
		log.info(exception.getMessage());
		WebSocketErrorResponse response = WebSocketErrorResponse.of(
			exception.getRoomId(),
			exception.getUserId(),
			exception.getErrorCode().getStatus(),
			exception.getErrorCode().getCode(),
			exception.getMessage());
		messagingTemplate.convertAndSend("/error/" + exception.getRoomId(), response);
	}

	@MessageExceptionHandler
	public void handleException(ChatRoomNotFoundWebSocketException exception) {
		log.info(exception.getMessage());
		WebSocketErrorResponse response = WebSocketErrorResponse.of(
			exception.getRoomId(),
			exception.getUserId(),
			exception.getErrorCode().getStatus(),
			exception.getErrorCode().getCode(),
			exception.getMessage());
		messagingTemplate.convertAndSend("/error/" + exception.getRoomId(), response);
	}

	@MessageExceptionHandler
	public void handleException(ChatRoomNotJoinWebSocketException exception) {
		log.info(exception.getMessage());
		WebSocketErrorResponse response = WebSocketErrorResponse.of(
			exception.getRoomId(),
			exception.getUserId(),
			exception.getErrorCode().getStatus(),
			exception.getErrorCode().getCode(),
			exception.getMessage());
		messagingTemplate.convertAndSend("/error/" + exception.getRoomId(), response);
	}

	@MessageExceptionHandler
	public void handleException(ChatCountLimitWebSocketException exception) {
		log.info(exception.getMessage());
		WebSocketErrorResponse response = WebSocketErrorResponse.of(
			exception.getRoomId(),
			exception.getUserId(),
			exception.getErrorCode().getStatus(),
			exception.getErrorCode().getCode(),
			exception.getMessage());
		messagingTemplate.convertAndSend("/error/" + exception.getRoomId(), response);
	}
}
