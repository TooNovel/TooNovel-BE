package com.yju.toonovel.global.security.jwt.exception;

import com.yju.toonovel.global.error.exception.ErrorCode;

public class ExpiredTokenException extends TokenException {

	public ExpiredTokenException() {
		super(ErrorCode.EXPIRED_TOKEN);
	}
}
