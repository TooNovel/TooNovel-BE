package com.yju.toonovel.domain.chatting.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.yju.toonovel.domain.chatting.dto.ChatDto;
import com.yju.toonovel.domain.chatting.dto.ChatRoomAllRequestDto;
import com.yju.toonovel.domain.chatting.dto.ChatRoomResponseDto;
import com.yju.toonovel.domain.chatting.entity.Chat;
import com.yju.toonovel.domain.chatting.entity.ChatRoom;
import com.yju.toonovel.domain.chatting.exception.ChatRoomAlreadyExistException;
import com.yju.toonovel.domain.chatting.exception.ChatRoomAlreadyJoinException;
import com.yju.toonovel.domain.chatting.exception.ChatRoomNotFoundException;
import com.yju.toonovel.domain.chatting.exception.ChatRoomNotJoinException;
import com.yju.toonovel.domain.chatting.exception.ChatRoomNotMatchUserException;
import com.yju.toonovel.domain.chatting.exception.NotAuthorException;
import com.yju.toonovel.domain.chatting.repository.ChatCustomRepository;
import com.yju.toonovel.domain.chatting.repository.ChatRoomRepository;
import com.yju.toonovel.domain.user.entity.Role;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {

	private final UserRepository userRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final ChatCustomRepository chatCustomRepository;

	// 채팅방 생성
	public void createChatRoom(ChatRoomAllRequestDto dto, Long userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		// 작가만 생성 가능
		if (user.getRole() != Role.AUTHOR) {
			throw new NotAuthorException();
		}

		// 작가당 1개의 채팅방만 생성 가능
		Optional<ChatRoom> chatRoom = chatRoomRepository.findByAuthorUserId(userId);
		if (chatRoom.isPresent()) {
			throw new ChatRoomAlreadyExistException();
		}

		ChatRoom result = chatRoomRepository.save(ChatRoom.of(dto.getChatRoomName(), user));

		// 생성 후 바로 셀프 가입
		joinChatRoom(result.getChatRoomId(), userId);
	}

	public void deleteChatRoom(Long rid, Long userId) {
		// 자신의 채팅방이 존재한다는 것은, registerChatRoom 시에 유저 및 작가 확인이 되었다는 뜻이므로 재확인하지 않았습니다

		// 채팅방 존재 여부 확인
		chatRoomRepository.findById(rid)
				.orElseThrow(() -> new ChatRoomNotFoundException());

		// 다른 사람의 채팅방을 삭제 시도하는 경우 방지
		chatRoomRepository.findByChatRoomIdAndAuthorUserId(rid, userId)
			.orElseThrow(() -> new ChatRoomNotMatchUserException());

		chatRoomRepository.deleteById(rid);
	}

	public void joinChatRoom(Long rid, Long userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		ChatRoom chatRoom = chatRoomRepository.findById(rid)
			.orElseThrow(() -> new ChatRoomNotFoundException());

		// 이미 가입되어 있는지 확인
		if (chatRoom.getUsers().contains(user)) {
			throw new ChatRoomAlreadyJoinException();
		}

		chatRoom.join(user);

		// 엔티티 자체가 변경된 것이 아니라, 엔티티 내부의 컬렉션이 변경된 것이므로 dirty checking에 걸리지 않습니다
		chatRoomRepository.save(chatRoom);
	}

	public List<ChatRoomResponseDto> getAllChatRoom(Long userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		return chatRoomRepository.findAllChatRoomByUser(user);
	}

	public List<ChatDto> getChatListByAuthor(Long userId, Long rid, Long chatId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		ChatRoom chatRoom = chatRoomRepository.findById(rid)
			.orElseThrow(() -> new ChatRoomNotFoundException());

		// 해당 채팅방에 가입되어 있는지 확인
		if (!chatRoom.getUsers().contains(user)) {
			throw new ChatRoomNotJoinException();
		}

		return (Objects.equals(chatRoom.getUser().getUserId(), user.getUserId()))
			? getChatListToAuthor(chatRoom, chatId)
			: getChatListToUser(chatRoom, user, chatId);
	}

	// 요청한 사람이 채팅방의 주인인 경우
	private List<ChatDto> getChatListToAuthor(ChatRoom chatRoom, Long chatId) {
		if (chatId == null) {
			return chatCustomRepository.findAllByChatRoomToAuthor(chatRoom)
				.stream().map(chat -> chatToChatDto(chat)).collect(Collectors.toList());
		} else {
			return chatCustomRepository.findAllByChatRoomAndChatIdToAuthor(chatRoom, chatId)
				.stream().map(chat -> chatToChatDto(chat)).collect(Collectors.toList());
		}
	}

	// 요청한 사람이 채팅방의 주인이 아닌 경우
	private List<ChatDto> getChatListToUser(ChatRoom chatRoom, User user, Long chatId) {
		if (chatId == null) {
			return chatCustomRepository
				.findAllByChatRoomToUser(chatRoom, user.getUserId(), chatRoom.getUser())
				.stream().map(chat -> chatToChatDto(chat)).collect(Collectors.toList());
		} else {
			return chatCustomRepository
				.findAllByChatRoomAndChatIdToUser(chatRoom, user.getUserId(), chatRoom.getUser(), chatId)
				.stream().map(chat -> chatToChatDto(chat)).collect(Collectors.toList());
		}
	}

	private ChatDto chatToChatDto(Chat chat) {
		return ChatDto.of(
			chat.getChatId(),
			chat.getUser().getNickname(),
			chat.getUser().getUserId(),
			chat.getUser().getRole(),
			chat.getMessage()
		);
	}

}
