package com.yju.toonovel.domain.user.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class AlreadyAuthorEnrollException extends BusinessException {

	public AlreadyAuthorEnrollException() {
		super(ErrorCode.AUTHOR_ALREADY_ENROLL);
	}
}
