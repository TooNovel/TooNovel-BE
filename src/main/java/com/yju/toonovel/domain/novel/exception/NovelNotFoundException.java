package com.yju.toonovel.domain.novel.exception;

import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.Getter;

@Getter
public class NovelNotFoundException extends RuntimeException {

	private final ErrorCode errorCode;

	public NovelNotFoundException() {
		this.errorCode = ErrorCode.NOVEL_NOT_FOUND;
	}

}
