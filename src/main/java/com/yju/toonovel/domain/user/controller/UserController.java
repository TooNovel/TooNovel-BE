package com.yju.toonovel.domain.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.novel.dto.LikeNovelPaginationResponseDto;
import com.yju.toonovel.domain.user.dto.UserProfileResponseDto;
import com.yju.toonovel.domain.user.dto.UserRegisterRequestDto;
import com.yju.toonovel.domain.user.dto.WriterRegisterRequestDto;
import com.yju.toonovel.domain.user.service.UserService;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	private final UserService userService;

	@Operation(summary = "유저(타인) 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 유저가 없는 상태일 때")
	@GetMapping("/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public UserProfileResponseDto getUserProfile(@PathVariable Long userId) {
		return userService.getUserProfile(userId);
	}

	@Operation(summary = "유저(본인) 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 유저가 없는 상태일 때")
	@GetMapping("/me")
	@ResponseStatus(HttpStatus.OK)
	public UserProfileResponseDto getMyProfile(@AuthenticationPrincipal JwtAuthentication user) {
		return userService.getUserProfile(user.userId);
	}

	@Operation(summary = "유저 탈퇴 요청")
	@ApiResponse(responseCode = "204", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 유저가 없는 상태일 때")
	@DeleteMapping("/me")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@AuthenticationPrincipal JwtAuthentication user) {
		userService.deleteUser(user.userId);
	}

	@Operation(summary = "유저 정보 수정 요청")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 유저가 없는 상태일 때")
	@PatchMapping("/me")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateUser(@RequestBody @Valid UserRegisterRequestDto requestDto,
		@AuthenticationPrincipal JwtAuthentication user) {
		userService.updateUser(user.userId, requestDto);
	}

	@Operation(summary = "좋아요한 소설 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@GetMapping("/novel")
	@ResponseStatus(HttpStatus.OK)
	public List<LikeNovelPaginationResponseDto> getAllLikeNovel(@AuthenticationPrincipal JwtAuthentication user,
		@RequestParam Long novelId) {
		return userService.findAllUserLike(novelId, user.userId);
	}

	@Operation(summary = "작가 신청 요청")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 유저가 없는 상태일 때")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void enrollWriter(@RequestBody WriterRegisterRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		userService.writerRegister(user.userId, dto);
	}

}
