package com.yju.toonovel.domain.statistics.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.statistics.dto.StatisticsResultResponseDto;
import com.yju.toonovel.domain.statistics.service.StatisticsService;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

	private final StatisticsService statisticsService;

	//작가 - 성별통계제공
	@Operation(summary = "성별 통계 데이터 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@GetMapping("/{nid}/gender")
	@ResponseStatus(HttpStatus.OK)
	public List<StatisticsResultResponseDto> genderStatistics(
		@PathVariable("nid") Long nid, @AuthenticationPrincipal JwtAuthentication user) {
		return statisticsService.getGenderStatistics(nid, user.userId);
	}

	//작가 - 나이통계제공
	@Operation(summary = "연령대별 통계 데이터 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@GetMapping("/{nid}/age")
	@ResponseStatus(HttpStatus.OK)
	public List<StatisticsResultResponseDto> ageStatistics(
		@PathVariable("nid") Long nid, @AuthenticationPrincipal JwtAuthentication user) {
		return statisticsService.getAgeStatistics(nid, user.userId);
	}
}
