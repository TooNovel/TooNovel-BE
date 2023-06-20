package com.yju.toonovel.domain.chatting.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.chatting.dto.ChatDto;
import com.yju.toonovel.domain.chatting.dto.ChatRoomCreateRequestDto;
import com.yju.toonovel.domain.chatting.dto.ChatRoomResponseDto;
import com.yju.toonovel.domain.chatting.dto.ReplyDto;
import com.yju.toonovel.domain.chatting.entity.Chat;
import com.yju.toonovel.domain.chatting.entity.ChatRoom;
import com.yju.toonovel.domain.chatting.exception.AuthorCannotLeaveChatRoomException;
import com.yju.toonovel.domain.chatting.exception.ChatRoomAlreadyExistException;
import com.yju.toonovel.domain.chatting.exception.ChatRoomAlreadyJoinException;
import com.yju.toonovel.domain.chatting.exception.ChatRoomNotFoundException;
import com.yju.toonovel.domain.chatting.exception.ChatRoomNotJoinException;
import com.yju.toonovel.domain.chatting.exception.ChatRoomNotMatchUserException;
import com.yju.toonovel.domain.chatting.exception.NotAuthorException;
import com.yju.toonovel.domain.chatting.repository.ChatCustomRepository;
import com.yju.toonovel.domain.chatting.repository.ChatRepository;
import com.yju.toonovel.domain.chatting.repository.ChatRoomRepository;
import com.yju.toonovel.domain.chatting.repository.ReplyCustomRepository;
import com.yju.toonovel.domain.chatting.repository.ReplyRepository;
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
	private final ReplyCustomRepository replyCustomRepository;
	private final ChatRepository chatRepository;
	private final ReplyRepository replyRepository;


	// 채팅방 생성
	public void createChatRoom(ChatRoomCreateRequestDto dto, Long userId) {
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
		joinChatRoomByUserId(userId, userId);
	}

	@Transactional
	public void deleteChatRoom(Long rid, Long userId) {
		// 자신의 채팅방이 존재한다는 것은, registerChatRoom 시에 유저 및 작가 확인이 되었다는 뜻이므로 재확인하지 않았습니다

		// 채팅방 존재 여부 확인
		ChatRoom chatRoom = chatRoomRepository.findById(rid)
				.orElseThrow(() -> new ChatRoomNotFoundException());

		// 다른 사람의 채팅방을 삭제 시도하는 경우 방지
		chatRoomRepository.findByChatRoomIdAndAuthorUserId(rid, userId)
			.orElseThrow(() -> new ChatRoomNotMatchUserException());

		replyRepository.deleteAllInBatchByChatRoom(chatRoom);
		chatRepository.deleteAllInBatchByChatRoom(chatRoom);

		chatRoomRepository.deleteById(rid);
	}

	@Transactional
	public void joinChatRoomByUserId(Long uid, Long userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		ChatRoom chatRoom = chatRoomRepository.findByAuthorUserId(uid)
			.orElseThrow(() -> new ChatRoomNotFoundException());

		// 이미 가입되어 있는지 확인
		if (chatRoom.getUsers().contains(user)) {
			throw new ChatRoomAlreadyJoinException();
		}

		chatRoom.join(user);

		// 엔티티 자체가 변경된 것이 아니라, 엔티티 내부의 컬렉션이 변경된 것이므로 dirty checking에 걸리지 않습니다
		chatRoomRepository.save(chatRoom);
	}

	public void leaveChatRoom(Long rid, Long userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		ChatRoom chatRoom = chatRoomRepository.findById(rid)
			.orElseThrow(() -> new ChatRoomNotFoundException());

		// 이미 가입되어 있는지 확인
		if (!(chatRoom.getUsers().contains(user))) {
			throw new ChatRoomNotJoinException();
		}

		// 작가는 탈퇴 불가
		if (chatRoom.getUser().getUserId() == userId) {
			throw new AuthorCannotLeaveChatRoomException();
		}

		chatRoom.leave(user);

		// 엔티티 자체가 변경된 것이 아니라, 엔티티 내부의 컬렉션이 변경된 것이므로 dirty checking에 걸리지 않습니다
		chatRoomRepository.save(chatRoom);
	}

	public List<ChatRoomResponseDto> getAllChatRoom(Long userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		return chatRoomRepository.findAllChatRoomByUser(user);
	}

	@Transactional
	public List<ChatDto> getChatList(Long userId, Long rid, LocalDate date) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		ChatRoom chatRoom = chatRoomRepository.findById(rid)
			.orElseThrow(() -> new ChatRoomNotFoundException());

		// 해당 채팅방에 가입되어 있는지 확인
		if (!chatRoom.getUsers().contains(user)) {
			throw new ChatRoomNotJoinException();
		}

		return (Objects.equals(chatRoom.getUser().getUserId(), user.getUserId()))
			? getChatListToAuthor(chatRoom, date)
			: getChatListToUser(chatRoom, user, date);
	}

	@Transactional
	public List<ReplyDto> getReplyList(Long userId, Long rid, LocalDate date) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		ChatRoom chatRoom = chatRoomRepository.findById(rid)
			.orElseThrow(() -> new ChatRoomNotFoundException());

		// 해당 채팅방에 가입되어 있는지 확인
		if (!chatRoom.getUsers().contains(user)) {
			throw new ChatRoomNotJoinException();
		}

		return replyCustomRepository.findAllByChatRoomAndDate(chatRoom, date);
	}

	// 요청한 사람이 채팅방의 주인인 경우
	private List<ChatDto> getChatListToAuthor(ChatRoom chatRoom, LocalDate date) {
		if (date == null) {
			return chatCustomRepository.findAllByChatRoomToAuthor(chatRoom)
				.stream().map(chat -> {
					// 채팅을 쓴 사람 == 채팅방 주인
					if (chat.getUser().getUserId().equals(chatRoom.getUser().getUserId())) {
						return chatToChatDto(chat, true);
					} else { // 채팅을 쓴 사람 != 채팅방 주인
						return chatToChatDto(chat, false);
					}
				}).collect(Collectors.toList());
		} else {
			return chatCustomRepository.findAllByChatRoomAndDateToAuthor(chatRoom, date)
				.stream().map(chat -> {
					// 채팅을 쓴 사람 == 채팅방 주인
					if (chat.getUser().getUserId().equals(chatRoom.getUser().getUserId())) {
						return chatToChatDto(chat, true);
					} else { // 채팅을 쓴 사람 != 채팅방 주인
						return chatToChatDto(chat, false);
					}
				}).collect(Collectors.toList());
		}
	}

	// 요청한 사람이 채팅방의 주인이 아닌 경우
	private List<ChatDto> getChatListToUser(ChatRoom chatRoom, User user, LocalDate date) {
		if (date == null) {
			return chatCustomRepository
				.findAllByChatRoomToUser(chatRoom, user.getUserId(), chatRoom.getUser())
				.stream().map(chat -> {
					// 채팅을 쓴 사람 == 채팅방 주인
					if (chat.getUser().getUserId().equals(chatRoom.getUser().getUserId())) {
						return chatToChatDto(chat, true);
					} else { // 채팅을 쓴 사람 != 채팅방 주인
						return chatToChatDto(chat, false);
					}
				}).collect(Collectors.toList());
		} else {
			return chatCustomRepository
				.findAllByChatRoomAndDateToUser(chatRoom, user.getUserId(), chatRoom.getUser(), date)
				.stream().map(chat -> {
					// 채팅을 쓴 사람 == 채팅방 주인
					if (chat.getUser().getUserId().equals(chatRoom.getUser().getUserId())) {
						return chatToChatDto(chat, true);
					} else { // 채팅을 쓴 사람 != 채팅방 주인
						return chatToChatDto(chat, false);
					}
				}).collect(Collectors.toList());
		}
	}

	private ChatDto chatToChatDto(Chat chat, boolean isCreator) {
		return ChatDto.of(
			chat.getChatId(),
			chat.getUser().getNickname(),
			chat.getUser().getUserId(),
			isCreator,
			chat.getMessage(),
			(chat.isFiltered()) ? "bad" : "ok",
			chat.getCreatedDate()
		);
	}

}
