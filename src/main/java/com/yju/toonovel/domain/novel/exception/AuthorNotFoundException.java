package com.yju.toonovel.domain.novel.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class AuthorNotFoundException extends BusinessException {

	public AuthorNotFoundException() {
		super(ErrorCode.AUTHOR_NOT_FOUND);
	}
}
