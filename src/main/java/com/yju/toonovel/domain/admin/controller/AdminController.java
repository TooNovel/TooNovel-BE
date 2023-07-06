package com.yju.toonovel.domain.admin.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.admin.dto.AdminStatisticsRequestDto;
import com.yju.toonovel.domain.admin.dto.EnrollListPaginationRequestDto;
import com.yju.toonovel.domain.admin.dto.EnrollListResponseDto;
import com.yju.toonovel.domain.admin.dto.EnrollUpdateRequestDto;
import com.yju.toonovel.domain.admin.service.AdminService;
import com.yju.toonovel.domain.statistics.dto.AdminStatisticsResponseDto;
import com.yju.toonovel.domain.statistics.service.StatisticsService;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "관리자 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

	private final AdminService adminService;
	private final StatisticsService statisticsService;

	@Operation(summary = "작가 신청 리스트 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<EnrollListResponseDto> findAllEnrollList(@ModelAttribute EnrollListPaginationRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		return adminService.findAllEnrollList(dto, user.userId);
	}

	@Operation(summary = "작가 승인 요청")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@PatchMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void updateAuthor(@RequestBody EnrollUpdateRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		adminService.updateAuthor(user.userId, dto);
	}

	@Operation(summary = "일별 리뷰 개수 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@GetMapping("/review")
	@ResponseStatus(HttpStatus.OK)
	public List<AdminStatisticsResponseDto> findReviewStatistic(@ModelAttribute AdminStatisticsRequestDto dto) {
		return statisticsService.findReviewStatistic(dto);
	}

	@Operation(summary = "일별 작품 개수 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@GetMapping("/novel")
	@ResponseStatus(HttpStatus.OK)
	public List<AdminStatisticsResponseDto> findNovelStatistic(@ModelAttribute AdminStatisticsRequestDto dto) {
		return statisticsService.findNovelStatistic(dto);
	}
}
