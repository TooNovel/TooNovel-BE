package com.yju.toonovel.domain.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.user.dto.UserProfileResponseDto;
import com.yju.toonovel.domain.user.dto.UserRegisterRequestDto;
import com.yju.toonovel.domain.user.service.UserService;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	private final UserService userService;

	@GetMapping("/{userId}")
	public UserProfileResponseDto getUserProfile(@PathVariable Long userId) {
		return userService.getUserProfile(userId);
	}

	@GetMapping("/me")
	public UserProfileResponseDto getMyProfile(@AuthenticationPrincipal JwtAuthentication user) {
		return userService.getUserProfile(user.userId);
	}

	@PatchMapping("/register")
	public void userRegister(@RequestBody UserRegisterRequestDto requestDto,
			@AuthenticationPrincipal JwtAuthentication user) {
		userService.register(user.userId, requestDto);
	}
}
