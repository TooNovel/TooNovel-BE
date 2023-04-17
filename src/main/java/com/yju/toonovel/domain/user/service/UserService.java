package com.yju.toonovel.domain.user.service;

import org.springframework.stereotype.Service;

import com.yju.toonovel.domain.user.dto.UserProfileResponseDto;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;

	public UserProfileResponseDto getUserProfile(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException());
		return new UserProfileResponseDto(user);
	}
}
