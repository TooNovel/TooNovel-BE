package com.yju.toonovel.global.websocket.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.global.security.jwt.JwtAuthentication;
import com.yju.toonovel.global.security.jwt.JwtAuthenticationToken;
import com.yju.toonovel.global.security.jwt.service.TokenService;
import com.yju.toonovel.global.websocket.dto.ChatByUserToAuthorDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class ChatController {

	private final SimpMessagingTemplate messagingTemplate; // @EnableWebSocketMessageBroker 에 의해 등록된 Bean
	private final TokenService tokenService;

	// 채팅방에 보내기 (작가 -> 독자들). 작동 확인 완료
	@MessageMapping("/chat/{roomId}")
	public void chatToReader(@Payload ChatByUserToAuthorDto dto, @DestinationVariable String roomId) {
		JwtAuthenticationToken token = tokenService.getAuthentication(dto.getSenderAccessToken());
		JwtAuthentication user = (JwtAuthentication) token.getPrincipal();
		dto.setSenderId(user.userId);
		log.info("--------------ChatController.chatToReader : " + dto + "Room ID: " + roomId);
		messagingTemplate.convertAndSend("/chat/" + roomId, dto.getMessage());
	}

	// 개인에게 보내기 (독자 -> 작가)
	@MessageMapping("/chat")
	public void chatToAuthor(@Payload ChatByUserToAuthorDto dto) {
		log.info("-----------ChatController.chatToAuthor : " + dto.toString());
	}
}
