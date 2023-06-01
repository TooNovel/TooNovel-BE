package com.yju.toonovel.domain.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.admin.dto.WriterRegisterRequestDto;
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

	@Operation(summary = "작가 신청 요청")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 유저가 없는 상태일 때")
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void enrollWriter(@RequestBody WriterRegisterRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		adminService.writerRegister(user.userId, dto);
	}
}
