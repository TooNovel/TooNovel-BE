package com.yju.toonovel.domain.user.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class InvalidUserException extends BusinessException {

	public InvalidUserException() {
		super(ErrorCode.USER_INVALID);
	}
}
