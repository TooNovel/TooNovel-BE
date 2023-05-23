package com.yju.toonovel.global.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.global.websocket.service.ChatRoomService;
import com.yju.toonovel.global.websocket.service.MessageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class MessageController {

	private final SimpMessagingTemplate messagingTemplate; // @EnableWebSocketMessageBroker 에 의해 등록된 Bean
	private final ChatRoomService chatRoomService;
	private final MessageService messageService;

	@MessageMapping("/chat/send")
	public void chat(String temp) {
		log.info(temp);
	}

}
