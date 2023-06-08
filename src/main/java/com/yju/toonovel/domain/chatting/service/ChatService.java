package com.yju.toonovel.domain.chatting.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.chatting.dto.ChatDto;
import com.yju.toonovel.domain.chatting.entity.Chat;
import com.yju.toonovel.domain.chatting.entity.ChatRoom;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatCountLimitWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatRoomNotFoundWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatRoomNotJoinWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.UserNotFoundWebSocketException;
import com.yju.toonovel.domain.chatting.repository.ChatCustomRepository;
import com.yju.toonovel.domain.chatting.repository.ChatRepository;
import com.yju.toonovel.domain.chatting.repository.ChatRoomRepository;
import com.yju.toonovel.domain.user.entity.Role;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.repository.UserRepository;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;
import com.yju.toonovel.global.security.jwt.JwtAuthenticationToken;
import com.yju.toonovel.global.security.jwt.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
	private final UserRepository userRepository;
	private final ChatRepository chatRepository;
	private final ChatCustomRepository chatCustomRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final TokenService tokenService;
	private final long chatLimit = 3;

	@Transactional
	public void authenticationAndSaveChat(ChatDto dto, String roomId) {
		// 유저 인증
		JwtAuthenticationToken token = tokenService.getAuthentication(dto.getSenderAccessToken());
		JwtAuthentication userJwt = (JwtAuthentication) token.getPrincipal();
		User user = userRepository.findByUserId(userJwt.userId)
			.orElseThrow(() -> new UserNotFoundWebSocketException(userJwt.userId, roomId));

		// 채팅방 존재 여부 확인
		ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(roomId))
			.orElseThrow(() -> new ChatRoomNotFoundWebSocketException(user.getUserId(), roomId));

		// 해당 채팅방에 가입되어 있는지 확인
		if (!chatRoom.getUsers().contains(user)) {
			throw new ChatRoomNotJoinWebSocketException(user.getUserId(), roomId);
		}

		// USER는 하루 채팅 3회 제한
		if (user.getRole() == Role.USER) {
			limitCheck(user, chatRoom, chatLimit);
		}

		// WebSocket 통신 전 필요한 데이터 set
		dto.setRole(user.getRole());
		dto.setSenderId(user.getUserId());

		// DB 작업
		chatRepository.save(Chat.of(dto.getMessage(), chatRoom, user));
	}

	private void limitCheck(User user, ChatRoom chatRoom, long limit) {
		List<Chat> recentChat =
			chatCustomRepository.findRecentChatByChatRoomAndUser(chatRoom, user.getUserId(), limit);
		LocalDate today = LocalDate.now();
		long todayChatCount = recentChat.stream().filter(chat -> {
			return chat.getCreatedDate().toLocalDate().isEqual(today);
		}).count();

		if (todayChatCount >= limit) {
			throw new ChatCountLimitWebSocketException(user.getUserId(), chatRoom.getChatRoomId().toString());
		}
	}
}
