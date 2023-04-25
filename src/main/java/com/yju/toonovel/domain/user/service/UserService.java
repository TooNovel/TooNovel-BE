package com.yju.toonovel.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.user.dto.UserProfileResponseDto;
import com.yju.toonovel.domain.user.dto.UserRegisterRequestDto;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;
import com.yju.toonovel.global.security.jwt.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;

	public UserProfileResponseDto getUserProfile(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException());
		return new UserProfileResponseDto(user);
	}

	@Transactional
	public void register(Long id, UserRegisterRequestDto requestDto) {
		User user = userRepository.findByUserId(id)
			.orElseThrow(() -> new UserNotFoundException());
		user.register(requestDto.getNickname(), requestDto.getGender(), requestDto.getBirth());
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
}
