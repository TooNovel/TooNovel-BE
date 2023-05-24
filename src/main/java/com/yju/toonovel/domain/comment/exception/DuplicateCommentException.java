package com.yju.toonovel.domain.comment.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class DuplicateCommentException extends BusinessException {

	public DuplicateCommentException() {
		super(ErrorCode.COMMENT_DUPLICATE);
	}
}
