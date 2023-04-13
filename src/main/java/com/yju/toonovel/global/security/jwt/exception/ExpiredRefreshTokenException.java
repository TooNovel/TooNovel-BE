package com.yju.toonovel.global.security.jwt.exception;

import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.Getter;

@Getter
public class ExpiredRefreshTokenException extends RuntimeException {

	private final ErrorCode errorCode;

	public ExpiredRefreshTokenException() {
		this.errorCode = ErrorCode.EXPIRED_REFRESH_TOKEN;
	}
}
