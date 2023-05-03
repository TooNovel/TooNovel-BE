package com.yju.toonovel.domain.user.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {

	public UserNotFoundException() {
		super(ErrorCode.USER_NOT_FOUND);
	}

}
