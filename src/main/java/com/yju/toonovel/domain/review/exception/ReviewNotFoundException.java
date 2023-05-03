package com.yju.toonovel.domain.review.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.Getter;

@Getter
public class ReviewNotFoundException extends BusinessException {
	public ReviewNotFoundException() {
		super(ErrorCode.REVIEW_NOT_FOUND);
	}
}
