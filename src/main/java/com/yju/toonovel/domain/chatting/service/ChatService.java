package com.yju.toonovel.domain.chatting.service;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.yju.toonovel.domain.chatting.dto.ChatDto;
import com.yju.toonovel.domain.chatting.dto.FilterChatRequestDto;
import com.yju.toonovel.domain.chatting.dto.FilterChatResponseDto;
import com.yju.toonovel.domain.chatting.dto.ReplyDto;
import com.yju.toonovel.domain.chatting.entity.Chat;
import com.yju.toonovel.domain.chatting.entity.ChatRoom;
import com.yju.toonovel.domain.chatting.entity.Reply;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatCountLimitWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatNotFoundWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatRoomNotFoundWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatRoomNotJoinWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.ChatRoomNotMatchUserWebSocketException;
import com.yju.toonovel.domain.chatting.exception.websocket.UserNotFoundWebSocketException;
import com.yju.toonovel.domain.chatting.repository.ChatCustomRepository;
import com.yju.toonovel.domain.chatting.repository.ChatRepository;
import com.yju.toonovel.domain.chatting.repository.ChatRoomRepository;
import com.yju.toonovel.domain.chatting.repository.ReplyRepository;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.repository.UserRepository;

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
	private final ReplyRepository replyRepository;
	private final long chatLimit = 3;

	@Value("${server.machineLearning}")
	private String machineLearningServer;

	@Transactional
	public void authenticationAndSaveChat(ChatDto dto, String roomId) {
		// 유저 존재 여부 확인
		User user = userRepository.findByUserId(dto.getSenderId())
			.orElseThrow(() -> new UserNotFoundWebSocketException(dto.getSenderId(), roomId));

		// 채팅방 존재 여부 확인
		ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(roomId))
			.orElseThrow(() -> new ChatRoomNotFoundWebSocketException(user.getUserId(), roomId));

		// 해당 채팅방에 가입되어 있는지 확인
		if (!chatRoom.getUsers().contains(user)) {
			throw new ChatRoomNotJoinWebSocketException(user.getUserId(), roomId);
		}

		// 작가 여부 확인
		boolean isCreator = chatRoom.getUser().getUserId() == user.getUserId();

		// 해당 채팅방의 생성자가 아니면 하루 채팅 3회 제한
		if (!isCreator) {
			limitCheck(user, chatRoom, chatLimit);
		}

		// DB 작업
		Chat chat = chatRepository.save(Chat.of(dto.getMessage(), chatRoom, user, false));

		//메시지 필터링
		FilterChatResponseDto response = filterChat(dto.getMessage());

		if (response.getFilteredResult().equals("bad")) {
			chat.filteringChat();
		}

		// WebSocket 통신 전 필요한 데이터 set
		dto.setChatId(chat.getChatId());
		dto.setSenderName(user.getNickname());
		dto.setCreator(isCreator);
		dto.setFilterResult(response.getFilteredResult());
	}

	public FilterChatResponseDto filterChat(String message) {
		RestTemplate restTemplate = new RestTemplate();

		URI uri = UriComponentsBuilder
			.fromUriString(machineLearningServer)
			.path("/filter")
			.encode()
			.build()
			.expand(message)
			.toUri();

		FilterChatRequestDto request = FilterChatRequestDto
			.builder()
			.message(message)
			.build();
		ResponseEntity<FilterChatResponseDto> response =
			restTemplate.postForEntity(uri, request, FilterChatResponseDto.class);

		return response.getBody();
	}

	@Transactional
	public void authenticationAndSaveReply(ReplyDto dto, String roomId) {
		// 유저 존재 여부 확인 (채팅방 생성자)
		User user = userRepository.findByUserId(dto.getSenderId())
			.orElseThrow(() -> new UserNotFoundWebSocketException(dto.getSenderId(), roomId));

		// 해당 채팅방의 작가인지 확인
		ChatRoom chatRoom = chatRoomRepository.findByChatRoomIdAndAuthorUserId(Long.valueOf(roomId), user.getUserId())
			.orElseThrow(() -> new ChatRoomNotMatchUserWebSocketException(user.getUserId(), roomId));

		// 해당 메세지가 존재하는지 확인
		Chat chat = chatRepository.findById(dto.getChatId())
			.orElseThrow(() -> new ChatNotFoundWebSocketException(user.getUserId(), roomId));

		// DB 작업
		Reply reply = replyRepository.save(Reply.of(dto.getReplyMessage(), chatRoom, user, chat));

		// WebSocket 통신 전 필요한 데이터 set
		dto.setReplyId(reply.getReplyId());
		dto.setSenderName(user.getNickname());
		dto.setUserName(chat.getUser().getNickname());
	}

	private void limitCheck(User user, ChatRoom chatRoom, long limit) {
		List<Chat> recentChat =
			chatCustomRepository.findRecentChatByChatRoomAndUser(chatRoom, user.getUserId(), limit);
		LocalDate today = LocalDate.now();
		long todayChatCount = recentChat
			.stream().filter(chat -> chat.getCreatedDate().toLocalDate().isEqual(today))
			.count();

		if (todayChatCount >= limit) {
			throw new ChatCountLimitWebSocketException(user.getUserId(), chatRoom.getChatRoomId().toString());
		}
	}
}
