package com.yju.toonovel.domain.review.exception;

import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.Getter;

@Getter
public class DuplicateReviewException extends RuntimeException { private final ErrorCode errorCode;

	public DuplicateReviewException() {
		this.errorCode = ErrorCode.REVIEW_ID_DUPLICATE;
	}
}
