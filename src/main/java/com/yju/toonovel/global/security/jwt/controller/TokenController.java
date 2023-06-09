package com.yju.toonovel.global.security.jwt.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.global.security.jwt.dto.TokenReIssueResponseDto;
import com.yju.toonovel.global.security.jwt.service.TokenService;
import com.yju.toonovel.global.util.CookieUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
public class TokenController {

	private final TokenService tokenService;

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> reIssueTokens(@CookieValue("refreshTokenCookie") String refreshToken) {

		TokenReIssueResponseDto tokenResponse = tokenService.reIssueTokens(refreshToken);

		HttpHeaders headers = new HttpHeaders();

		headers.add("Set-Cookie", tokenResponse.getAccessToken());
		headers.add("Set-Cookie", tokenResponse.getRefreshToken());

		return ResponseEntity.ok()
			.headers(headers)
			.build();
	}

	@DeleteMapping()
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteRefreshToken(@CookieValue("refreshTokenCookie") String refreshToken) {

		tokenService.deleteRefreshToken(refreshToken);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", CookieUtils.getEmptyCookie("refreshTokenCookie"));

		return ResponseEntity.ok()
			.headers(headers)
			.build();
	}

}
