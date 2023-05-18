package com.yju.toonovel.global.security.jwt.exception;

import com.yju.toonovel.global.error.exception.ErrorCode;

public class InvalidTokenException extends TokenException {

	public InvalidTokenException() {
		super(ErrorCode.INVALID_TOKEN);
	}
}
