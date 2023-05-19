package com.yju.toonovel.domain.post.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class PostAlreadyLikedException extends BusinessException {
	public PostAlreadyLikedException() {
		super(ErrorCode.POST_ALREADY_LIKED);
	}
}
