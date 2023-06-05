package com.yju.toonovel.domain.admin.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class EnrollNotFoundException extends BusinessException {
	public EnrollNotFoundException() {
		super(ErrorCode.ENROLL_NOT_FOUND);
	}
}
