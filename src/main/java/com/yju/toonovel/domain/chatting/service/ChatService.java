package com.yju.toonovel.domain.chatting.service;

import org.springframework.stereotype.Service;

import com.yju.toonovel.domain.chatting.dto.ChatDto;
import com.yju.toonovel.domain.chatting.entity.Chat;
import com.yju.toonovel.domain.chatting.entity.ChatRoom;
import com.yju.toonovel.domain.chatting.exception.ChatRoomNotFoundException;
import com.yju.toonovel.domain.chatting.repository.ChatRepository;
import com.yju.toonovel.domain.chatting.repository.ChatRoomRepository;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;
import com.yju.toonovel.global.security.jwt.JwtAuthenticationToken;
import com.yju.toonovel.global.security.jwt.service.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
	private final UserRepository userRepository;
	private final ChatRepository chatRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final TokenService tokenService;

	public void authenticationAndSaveChat(ChatDto dto, String roomId) {
		// 유저 인증
		JwtAuthenticationToken token = tokenService.getAuthentication(dto.getSenderAccessToken());
		JwtAuthentication userJwt = (JwtAuthentication) token.getPrincipal();
		User user = userRepository.findByUserId(userJwt.userId)
			.orElseThrow(() -> new UserNotFoundException());

		// 채팅방 존재 여부 확인
		ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(roomId))
			.orElseThrow(() -> new ChatRoomNotFoundException());

		// WebSocket 통신 전 필요한 데이터 set
		dto.setRole(user.getRole());
		dto.setSenderId(user.getUserId());

		// DB 작업
		chatRepository.save(Chat.of(dto.getMessage(), chatRoom, user));

	}
}
