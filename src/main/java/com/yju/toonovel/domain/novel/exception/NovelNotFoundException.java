package com.yju.toonovel.domain.novel.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class NovelNotFoundException extends BusinessException {

	public NovelNotFoundException() {
		super(ErrorCode.NOVEL_NOT_FOUND);
	}

}
