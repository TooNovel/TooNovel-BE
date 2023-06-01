package com.yju.toonovel.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.admin.entity.EnrollHistory;
import com.yju.toonovel.domain.admin.repository.EnrollRepository;
import com.yju.toonovel.domain.novel.dto.LikeNovelPaginationResponseDto;
import com.yju.toonovel.domain.novel.exception.AuthorNotFoundException;
import com.yju.toonovel.domain.novel.repository.LikeNovelRepository;
import com.yju.toonovel.domain.novel.repository.NovelRepository;
import com.yju.toonovel.domain.user.dto.AuthorRegisterRequestDto;
import com.yju.toonovel.domain.user.dto.UserProfileResponseDto;
import com.yju.toonovel.domain.user.dto.UserRegisterRequestDto;
import com.yju.toonovel.domain.user.entity.Role;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.AlreadyAuthorException;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;
import com.yju.toonovel.global.security.jwt.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final LikeNovelRepository likeNovelRepository;
	private final NovelRepository novelRepository;
	private final EnrollRepository enrollRepository;

	public UserProfileResponseDto getUserProfile(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException());
		return new UserProfileResponseDto(user);
	}

	@Transactional
	public void updateUser(Long id, UserRegisterRequestDto requestDto) {
		User user = userRepository.findByUserId(id)
			.orElseThrow(() -> new UserNotFoundException());
		user.update(requestDto.getNickname(), requestDto.getGender(), requestDto.getBirth());
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

	@Transactional
	public List<LikeNovelPaginationResponseDto> findAllUserLike(Long novelId, Long userId) {
		return likeNovelRepository
			.findAllUserLikeNovel(userId, novelId)
			.stream()
			.map(likenovel -> LikeNovelPaginationResponseDto.from(likenovel))
			.collect(Collectors.toList());
	}

	@Transactional
	public void authorRegister(Long userId, AuthorRegisterRequestDto dto) {

		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		if (user.getRole() == Role.AUTHOR) {
			throw new AlreadyAuthorException();
		}

		novelRepository.findByAuthor(dto.getNickname())
			.ifPresentOrElse(
				isAuthor -> {
					isAuthor.forEach(i -> {
						i.updateUserId(user);
					});
					enrollRepository.save(EnrollHistory.of(user));
				},
				() -> new AuthorNotFoundException()
			);
	}

}
