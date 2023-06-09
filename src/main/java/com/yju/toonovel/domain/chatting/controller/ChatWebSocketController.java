package com.yju.toonovel.domain.chatting.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.chatting.dto.ChatDto;
import com.yju.toonovel.domain.chatting.dto.ReplyDto;
import com.yju.toonovel.domain.chatting.exception.WebSocketErrorResponse;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatCountLimitWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatNotFoundWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatRoomNotFoundWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatRoomNotJoinWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatRoomNotMatchUserWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.UserNotFoundWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.WebSocketExceptionInterface;
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

	@MessageMapping("/reply/{roomId}")
	public void reply(@Payload ReplyDto dto, @DestinationVariable String roomId) {
		chatService.authenticationAndSaveReply(dto, roomId);
		messagingTemplate.convertAndSend("/reply/" + roomId, dto);
	}

	@MessageExceptionHandler
	public void handleException(UserNotFoundWebSocketException exception) {
		sendException(exception);
	}

	@MessageExceptionHandler
	public void handleException(ChatRoomNotFoundWebSocketException exception) {
		sendException(exception);
	}

	@MessageExceptionHandler
	public void handleException(ChatRoomNotJoinWebSocketException exception) {
		sendException(exception);
	}

	@MessageExceptionHandler
	public void handleException(ChatCountLimitWebSocketException exception) {
		sendException(exception);
	}

	@MessageExceptionHandler
	public void handleException(ChatRoomNotMatchUserWebSocketException exception) {
		sendException(exception);
	}

	@MessageExceptionHandler
	public void handleException(ChatNotFoundWebSocketException exception) {
		sendException(exception);
	}


	private void sendException(WebSocketExceptionInterface exception) {
		log.info(exception.getMessage());
		WebSocketErrorResponse response = WebSocketErrorResponse.of(
			exception.getRoomId(),
			exception.getUserId(),
			exception.getStatus(),
			exception.getCode(),
			exception.getMessage()
		);
		messagingTemplate.convertAndSend("/error/" + exception.getRoomId(), response);
	}
}
