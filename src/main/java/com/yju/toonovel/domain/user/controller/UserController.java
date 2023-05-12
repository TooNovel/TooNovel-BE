package com.yju.toonovel.domain.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.novel.dto.LikeNovelPaginationResponseDto;
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
	@ResponseStatus(HttpStatus.OK)
	public UserProfileResponseDto getUserProfile(@PathVariable Long userId) {
		return userService.getUserProfile(userId);
	}

	@GetMapping("/me")
	@ResponseStatus(HttpStatus.OK)
	public UserProfileResponseDto getMyProfile(@AuthenticationPrincipal JwtAuthentication user) {
		return userService.getUserProfile(user.userId);
	}

	@DeleteMapping("/me")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@AuthenticationPrincipal JwtAuthentication user) {
		userService.deleteUser(user.userId);
	}

	@PatchMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void userRegister(@RequestBody UserRegisterRequestDto requestDto,
		@AuthenticationPrincipal JwtAuthentication user) {
		userService.register(user.userId, requestDto);
	}

	@GetMapping("/novel")
	@ResponseStatus(HttpStatus.OK)
	public List<LikeNovelPaginationResponseDto> getAllLikeNovel(@AuthenticationPrincipal JwtAuthentication user,
		@RequestParam Long novelId) {
		return userService.findAllUserLike(novelId, user.userId);
	}

}
