package com.yju.toonovel.global.security.jwt.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class RefreshTokenNotFoundException extends BusinessException {
	public RefreshTokenNotFoundException() {
		super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
	}
}
