package com.yju.toonovel.domain.recommend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.novel.dto.NovelPaginationResponseDto;
import com.yju.toonovel.domain.recommend.service.RecommendService;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recommend")
@RequiredArgsConstructor
public class RecommendController {
	private final RecommendService recommendService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<NovelPaginationResponseDto> getNovelRecommend(@AuthenticationPrincipal JwtAuthentication user) {
		return recommendService.getNovelRecommend(user.userId);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void updateRecommendationModel() {
		recommendService.updateRecommendationModel();
	}
}
