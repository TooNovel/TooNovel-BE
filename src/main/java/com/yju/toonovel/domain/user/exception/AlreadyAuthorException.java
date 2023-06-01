package com.yju.toonovel.domain.user.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class AlreadyAuthorException extends BusinessException {

	public AlreadyAuthorException() {
		super(ErrorCode.ALREADY_AUTHOR);
	}
}
