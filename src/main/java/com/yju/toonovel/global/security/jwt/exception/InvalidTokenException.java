package com.yju.toonovel.global.security.jwt.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class InvalidTokenException extends BusinessException {

	public InvalidTokenException() {
		super(ErrorCode.INVALID_TOKEN);
	}
}
