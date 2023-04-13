package com.yju.toonovel.domain.user.exception;

import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

	private final ErrorCode errorCode;

	public UserNotFoundException() {
		this.errorCode = ErrorCode.USER_NOT_FOUND;
	}

}
