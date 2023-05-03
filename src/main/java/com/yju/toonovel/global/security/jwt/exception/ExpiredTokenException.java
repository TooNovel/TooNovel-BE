package com.yju.toonovel.global.security.jwt.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class ExpiredTokenException extends BusinessException {

	public ExpiredTokenException() {
		super(ErrorCode.EXPIRED_TOKEN);
	}
}
