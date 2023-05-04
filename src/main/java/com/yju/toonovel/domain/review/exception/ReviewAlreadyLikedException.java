package com.yju.toonovel.domain.review.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class ReviewAlreadyLikedException extends BusinessException {

	public ReviewAlreadyLikedException() {
		super(ErrorCode.REVIEW_ALREADY_LIKED);
	}
}
