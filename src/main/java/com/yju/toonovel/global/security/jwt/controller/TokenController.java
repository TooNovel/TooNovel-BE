package com.yju.toonovel.global.security.jwt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.global.security.jwt.dto.TokenReIssueResponseDto;
import com.yju.toonovel.global.security.jwt.service.TokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
public class TokenController {

	private final TokenService tokenService;

	@Value("${jwt.header.refresh-token}")
	final String refreshTokenHeader;

	@PostMapping(value = "", headers = "Authorization-refresh")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> reIssueTokens(
		@RequestHeader("Authorization-refresh") String refreshToken) {

		TokenReIssueResponseDto tokenResponse = tokenService.reIssueTokens(refreshToken);

		// 헤더에 액세스 토큰과 리프레쉬 토큰 값을 설정
		HttpHeaders headers = new HttpHeaders();

		headers.add("Set-Cookie", tokenResponse.getAccessToken());
		headers.add("Set-Cookie", tokenResponse.getRefreshToken());

		return ResponseEntity.ok()
			.headers(headers)
			.build();
	}

}
