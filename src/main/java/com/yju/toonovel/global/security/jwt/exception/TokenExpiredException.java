package com.yju.toonovel.global.security.jwt.exception;

import com.yju.toonovel.global.error.exception.ErrorCode;

public class TokenExpiredException extends TokenException {

	public TokenExpiredException() {
		super(ErrorCode.TOKEN_EXPIRED);
	}
}
