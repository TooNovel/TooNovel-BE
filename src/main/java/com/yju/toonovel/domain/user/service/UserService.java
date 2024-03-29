package com.yju.toonovel.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.admin.entity.EnrollHistory;
import com.yju.toonovel.domain.admin.repository.EnrollRepository;
import com.yju.toonovel.domain.chatting.entity.ChatRoom;
import com.yju.toonovel.domain.novel.dto.LikeNovelPaginationResponseDto;
import com.yju.toonovel.domain.novel.exception.AuthorNotFoundException;
import com.yju.toonovel.domain.novel.repository.LikeNovelRepository;
import com.yju.toonovel.domain.novel.repository.NovelRepository;
import com.yju.toonovel.domain.user.dto.AuthorListPaginationRequestDto;
import com.yju.toonovel.domain.user.dto.AuthorListResponseDto;
import com.yju.toonovel.domain.user.dto.AuthorRegisterRequestDto;
import com.yju.toonovel.domain.user.dto.UserMyProfileResponseDto;
import com.yju.toonovel.domain.user.dto.UserProfileResponseDto;
import com.yju.toonovel.domain.user.dto.UserProfileUpdateRequestDto;
import com.yju.toonovel.domain.user.dto.UserRegisterRequestDto;
import com.yju.toonovel.domain.user.entity.Role;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.AlreadyAuthorEnrollException;
import com.yju.toonovel.domain.user.exception.AlreadyAuthorException;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.ChatRoomCustomRepository;
import com.yju.toonovel.domain.user.repository.EnrollCustomRepository;
import com.yju.toonovel.domain.user.repository.UserRepository;
import com.yju.toonovel.global.security.jwt.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final LikeNovelRepository likeNovelRepository;
	private final NovelRepository novelRepository;
	private final EnrollRepository enrollRepository;
	private final EnrollCustomRepository enrollCustomRepository;
	private final ChatRoomCustomRepository chatRoomCustomRepository;

	public UserProfileResponseDto findUserProfile(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException());
		return UserProfileResponseDto.from(user);
	}

	public UserMyProfileResponseDto findMyProfile(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException());
		return UserMyProfileResponseDto.from(user);
	}

	@Transactional
	public void registerUser(Long userId, UserRegisterRequestDto requestDto) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());
		user.register(requestDto.getNickname(), requestDto.getGender(), requestDto.getBirth());
	}

	@Transactional
	public void updateUserProfile(Long userId, UserProfileUpdateRequestDto requestDto) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());
		user.updateProfile(requestDto.getNickname(), requestDto.getImageUrl());
	}

	@Transactional
	public void deleteUser(Long userId) {
		userRepository.findById(userId)
			.ifPresentOrElse(
				user -> userRepository.delete(user),
				() -> {
					throw new UserNotFoundException();
				});
		refreshTokenRepository.findByUserId(userId)
			.ifPresent(
				token -> refreshTokenRepository.delete(token)
			);
	}

	public List<LikeNovelPaginationResponseDto> findAllUserLike(Long novelId, Long userId) {
		return likeNovelRepository
			.findAllUserLikeNovel(userId, novelId)
			.stream()
			.map(likenovel -> LikeNovelPaginationResponseDto.from(likenovel))
			.collect(Collectors.toList());
	}

	@Transactional
	public void authorEnroll(Long userId, AuthorRegisterRequestDto dto) {

		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		if (user.getRole() == Role.AUTHOR) {
			throw new AlreadyAuthorException();
		}

		enrollRepository.findByUserId(userId)
			.ifPresent(isAlready -> {
				throw new AlreadyAuthorEnrollException();
			});

		novelRepository.findByAuthor(dto.getNickname())
			.ifPresentOrElse(
				isAuthor -> enrollRepository.save(EnrollHistory.of(dto.getNickname(), user)),
				() -> {
					throw new AuthorNotFoundException();
				}
			);
	}

	public List<AuthorListResponseDto> findAuthor(AuthorListPaginationRequestDto dto) {
		return enrollCustomRepository.findAllAuthor(dto);
	}

	public List<AuthorListResponseDto> findPopularChatRoomAuthor() {
		List<ChatRoom> chatRoomList = chatRoomCustomRepository.findByChatRoomMemberCount();

		return chatRoomList.stream().map(chatRoom -> {
			User author = chatRoom.getUser();
			return AuthorListResponseDto.of(author, null);
		}).collect(Collectors.toList());
	}
}
