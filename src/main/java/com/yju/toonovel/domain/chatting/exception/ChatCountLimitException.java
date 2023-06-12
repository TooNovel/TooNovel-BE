package com.yju.toonovel.domain.chatting.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class ChatCountLimitException extends BusinessException {
	public ChatCountLimitException() {
		super(ErrorCode.CHAT_COUNT_LIMIT);
	}
}
