package com.yju.toonovel.domain.novel.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.novel.dto.NovelDetailResponseDto;
import com.yju.toonovel.domain.novel.dto.NovelPaginationRequestDto;
import com.yju.toonovel.domain.novel.dto.NovelPaginationResponseDto;
import com.yju.toonovel.domain.novel.dto.UserLikeCheckResponseDto;
import com.yju.toonovel.domain.novel.service.NovelService;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "소설 API")
@RestController
@RequestMapping("/api/v1/novel")
@RequiredArgsConstructor
public class NovelController {

	private final NovelService novelService;

	@Operation(summary = "소설 전체 조회")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<NovelPaginationResponseDto> getAllNovel(@ModelAttribute NovelPaginationRequestDto requestDto) {
		return novelService.findAll(requestDto);
	}

	@Operation(summary = "소설 상세 조회")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 소설 혹은 플랫폼의 정보가 없는 상태일 때")
	@GetMapping("/{novelId}")
	@ResponseStatus(HttpStatus.OK)
	public NovelDetailResponseDto getSelectNovel(@PathVariable Long novelId) {
		return novelService.findById(novelId);
	}

	@Operation(summary = "소설 좋아요 요청, 좋아요 취소 요청")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 유저 혹은 소설이 없는 상태일 때")
	@PutMapping("/{novelId}/like")
	@ResponseStatus(HttpStatus.CREATED)
	public void toggleLike(
		@AuthenticationPrincipal JwtAuthentication user,
		@PathVariable Long novelId
	) {
		novelService.toggleNovelLike(user.userId, novelId);
	}

	@Operation(summary = "소설에 좋아요를 한 상태인지 체크 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 소설이 없는 상태일 때")
	@GetMapping("/{novelId}/like")
	@ResponseStatus(HttpStatus.OK)
	public UserLikeCheckResponseDto isUserLikes(
		@AuthenticationPrincipal JwtAuthentication user,
		@PathVariable long novelId) {
		return novelService.isUserLikes(user.userId, novelId);
	}
}
