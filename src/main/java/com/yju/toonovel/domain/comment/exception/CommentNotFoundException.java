package com.yju.toonovel.domain.comment.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.Getter;

@Getter
public class CommentNotFoundException extends BusinessException {

	public CommentNotFoundException() {
		super(ErrorCode.COMMENT_NOT_FOUND);
	}
}
