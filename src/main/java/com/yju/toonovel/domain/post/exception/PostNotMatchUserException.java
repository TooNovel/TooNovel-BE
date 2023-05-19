package com.yju.toonovel.domain.post.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class PostNotMatchUserException extends BusinessException {

	public PostNotMatchUserException() {
		super(ErrorCode.POST_NOT_MATCH_USER);
	}
}
