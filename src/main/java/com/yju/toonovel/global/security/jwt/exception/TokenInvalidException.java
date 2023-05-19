package com.yju.toonovel.global.security.jwt.exception;

import com.yju.toonovel.global.error.exception.ErrorCode;

public class TokenInvalidException extends TokenException {

	public TokenInvalidException() {
		super(ErrorCode.TOKEN_INVALID);
	}
}
