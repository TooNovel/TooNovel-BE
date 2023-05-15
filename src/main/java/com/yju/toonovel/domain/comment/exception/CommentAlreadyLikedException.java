package com.yju.toonovel.domain.comment.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class CommentAlreadyLikedException extends BusinessException {
	public CommentAlreadyLikedException() {
		super(ErrorCode.COMMENT_ALREADY_LIKED);
	}
}
