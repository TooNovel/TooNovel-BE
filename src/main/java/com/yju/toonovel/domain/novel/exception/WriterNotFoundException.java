package com.yju.toonovel.domain.novel.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class WriterNotFoundException extends BusinessException {

	public WriterNotFoundException() {
		super(ErrorCode.WRITER_NOT_FOUND);
	}
}
