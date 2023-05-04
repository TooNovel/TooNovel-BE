package com.yju.toonovel.domain.review.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class ReviewNotMatchUserException extends BusinessException {

	public ReviewNotMatchUserException() {
		super(ErrorCode.REVIEW_NOT_MATCH_USER);
	}
}
