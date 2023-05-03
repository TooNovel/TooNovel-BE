package com.yju.toonovel.domain.review.exception;

import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.Getter;

@Getter
public class ReviewNotFoundException extends RuntimeException {
	private final ErrorCode errorCode;

	public ReviewNotFoundException() {
		this.errorCode = ErrorCode.REVIEW_NOT_FOUND;
	}
}
