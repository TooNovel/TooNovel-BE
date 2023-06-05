package com.yju.toonovel.domain.admin.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class AdminAuthenticationFailException extends BusinessException {

	public AdminAuthenticationFailException() {
		super(ErrorCode.ADMIN_AUTHENTICATION_FAIL);
	}
}
