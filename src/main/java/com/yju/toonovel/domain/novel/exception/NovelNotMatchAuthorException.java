package com.yju.toonovel.domain.novel.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class NovelNotMatchAuthorException extends BusinessException {

	public NovelNotMatchAuthorException() {
		super(ErrorCode.NOVEL_NOT_MATCH_AUTHOR);
	}
}
