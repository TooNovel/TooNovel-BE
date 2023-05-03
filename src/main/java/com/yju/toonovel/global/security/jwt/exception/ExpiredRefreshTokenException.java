package com.yju.toonovel.global.security.jwt.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class ExpiredRefreshTokenException extends BusinessException {

	public ExpiredRefreshTokenException() {
		super(ErrorCode.EXPIRED_REFRESH_TOKEN);
	}
}
