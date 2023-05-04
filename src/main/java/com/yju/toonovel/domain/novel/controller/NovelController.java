package com.yju.toonovel.domain.novel.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.novel.dto.NovelDetailResponseDto;
import com.yju.toonovel.domain.novel.dto.NovelPaginationRequestDto;
import com.yju.toonovel.domain.novel.dto.NovelPaginationResponseDto;
import com.yju.toonovel.domain.novel.dto.UserLikeCheckResponseDto;
import com.yju.toonovel.domain.novel.service.NovelService;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/novel")
@RequiredArgsConstructor
public class NovelController {

	private final NovelService novelService;

	@GetMapping
	public List<NovelPaginationResponseDto> getAllNovel(@ModelAttribute NovelPaginationRequestDto requestDto) {
		return novelService.findAll(requestDto);
	}

	@GetMapping("/{novelId}")
	public NovelDetailResponseDto getSelectNovel(@PathVariable Long novelId) {
		return novelService.findById(novelId);
	}

	@PutMapping("/{novelId}/like")
	public void toggleLike(
		@AuthenticationPrincipal JwtAuthentication user,
		@PathVariable Long novelId
	) {
		novelService.toggleNovelLike(user.userId, novelId);
	}

	@GetMapping("/{novelId}/like")
	public UserLikeCheckResponseDto isUserLikes(
		@AuthenticationPrincipal JwtAuthentication user,
		@PathVariable long novelId) {
		return novelService.isUserLikes(user.userId, novelId);
	}
}
