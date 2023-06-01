package com.yju.toonovel.domain.admin.controller;

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

import com.yju.toonovel.domain.admin.dto.EnrollListPaginationRequestDto;
import com.yju.toonovel.domain.admin.dto.EnrollListResponseDto;
import com.yju.toonovel.domain.admin.dto.EnrollUpdateRequestDto;
import com.yju.toonovel.domain.admin.service.AdminService;
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

	@Operation(summary = "작가 신청 리스트 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<EnrollListResponseDto> getEnrollList(@ModelAttribute EnrollListPaginationRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		return adminService.getEnrollList(dto, user.userId);
	}

	@Operation(summary = "작가 승인 요청")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@PatchMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void updateWriter(@RequestBody EnrollUpdateRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		adminService.updateWriter(user.userId, dto);
	}
}
