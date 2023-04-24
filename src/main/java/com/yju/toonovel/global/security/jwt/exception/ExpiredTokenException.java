package com.yju.toonovel.global.security.jwt.exception;

import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.Getter;

@Getter
public class ExpiredTokenException extends RuntimeException {

	private final ErrorCode errorCode;

	public ExpiredTokenException() {
		this.errorCode = ErrorCode.EXPIRED_TOKEN;
	}
}
