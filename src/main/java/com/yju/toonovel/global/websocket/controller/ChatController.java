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
import com.yju.toonovel.global.websocket.dto.ChatDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class ChatController {

	private final SimpMessagingTemplate messagingTemplate;
	private final TokenService tokenService;

	@MessageMapping("/chat/{roomId}")
	public void chatToReader(@Payload ChatDto dto, @DestinationVariable String roomId) {
		JwtAuthenticationToken token = tokenService.getAuthentication(dto.getSenderAccessToken());
		JwtAuthentication user = (JwtAuthentication) token.getPrincipal();
		log.info(user.userId.toString()); // DB Insert용
		dto.setSenderRole(user.role);
		messagingTemplate.convertAndSend("/chat/" + roomId, dto);
	}
}
