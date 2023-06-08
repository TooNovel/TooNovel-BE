package com.yju.toonovel.domain.chatting.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class ChatNotFoundException extends BusinessException {
	public ChatNotFoundException() {
		super(ErrorCode.CHAT_NOT_FOUNT);
	}
}
