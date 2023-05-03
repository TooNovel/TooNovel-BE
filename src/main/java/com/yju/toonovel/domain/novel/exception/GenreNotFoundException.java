package com.yju.toonovel.domain.novel.exception;

import com.yju.toonovel.global.error.exception.ErrorCode;

public class GenreNotFoundException extends RuntimeException {
	private final ErrorCode errorCode;

	public GenreNotFoundException() {
		this.errorCode = ErrorCode.NOVEL_GENRE_NOT_FOUND;
	}
}
