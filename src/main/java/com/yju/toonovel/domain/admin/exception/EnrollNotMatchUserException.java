package com.yju.toonovel.domain.admin.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class EnrollNotMatchUserException extends BusinessException {

	public EnrollNotMatchUserException() {
		super(ErrorCode.ENROLL_NOT_MATCH_USER);
	}
}
