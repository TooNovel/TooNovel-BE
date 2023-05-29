package com.yju.toonovel.domain.chatting.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class NotAuthorException extends BusinessException {

	public NotAuthorException() {
		super(ErrorCode.HANDLE_ACCESS_DENIED);
	}

}
