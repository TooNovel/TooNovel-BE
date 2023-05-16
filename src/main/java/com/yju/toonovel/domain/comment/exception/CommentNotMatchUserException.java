package com.yju.toonovel.domain.comment.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class CommentNotMatchUserException extends BusinessException {

	public CommentNotMatchUserException() {
		super(ErrorCode.COMMENT_NOT_MATCH_USER);
	}
}
