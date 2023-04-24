package com.yju.toonovel.global.security.jwt.exception;

import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {

	private final ErrorCode errorCode;

	public InvalidTokenException() {
		this.errorCode = ErrorCode.INVALID_TOKEN;
	}
}
