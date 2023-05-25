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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "소설 추천 API")
@RestController
@RequestMapping("/api/v1/recommend")
@RequiredArgsConstructor
public class RecommendController {
	private final RecommendService recommendService;

	@Operation(summary = "추천 작품 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<NovelPaginationResponseDto> getNovelRecommend(@AuthenticationPrincipal JwtAuthentication user) {
		return recommendService.getNovelRecommend(user.userId);
	}

	@Operation(summary = "추천 모델 최신화 요청")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@PutMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void updateRecommendationModel() {
		recommendService.updateRecommendationModel();
	}
}
