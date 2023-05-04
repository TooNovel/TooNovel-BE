package com.yju.toonovel.domain.novel.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class GenreNotFoundException extends BusinessException {
	public GenreNotFoundException() {
		super(ErrorCode.NOVEL_GENRE_NOT_FOUND);
	}
}
