package com.yju.toonovel.domain.novel.exception;

import com.yju.toonovel.global.error.exception.ErrorCode;

import lombok.Getter;

@Getter
public class PlatformNotFoundException extends RuntimeException {

	private final ErrorCode errorCode;

	public PlatformNotFoundException() {
		this.errorCode = ErrorCode.PLATFORM_NOT_FOUND;
	}

}
