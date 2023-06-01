package com.yju.toonovel.domain.user.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class AlreadyWriterException extends BusinessException {

	public AlreadyWriterException() {
		super(ErrorCode.ALREADY_WRITER);
	}
}
