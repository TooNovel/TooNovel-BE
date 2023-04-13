package com.yju.toonovel.domain.user.exception;

import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.Getter;

@Getter
public class InvalidUserException extends RuntimeException {

	private final ErrorCode errorCode;

	public InvalidUserException() {
		this.errorCode = ErrorCode.USER_INVALID;
	}
}
