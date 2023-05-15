package com.yju.toonovel.domain.post.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class PostNotFoundException extends BusinessException {

	public PostNotFoundException() {
		super(ErrorCode.POST_NOT_FOUND);
	}
}
