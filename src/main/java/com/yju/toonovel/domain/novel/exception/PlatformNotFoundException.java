package com.yju.toonovel.domain.novel.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class PlatformNotFoundException extends BusinessException {

	public PlatformNotFoundException() {
		super(ErrorCode.PLATFORM_NOT_FOUND);
	}

}
