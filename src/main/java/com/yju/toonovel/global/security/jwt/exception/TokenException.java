package com.yju.toonovel.global.security.jwt.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class TokenException extends BusinessException {
	public TokenException(ErrorCode errorCode) {
		super(errorCode);
	}
}
