package com.yju.toonovel.domain.review.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class DuplicateReviewException extends BusinessException {
	public DuplicateReviewException() {
		super(ErrorCode.REVIEW_DUPLICATE);
	}
}
