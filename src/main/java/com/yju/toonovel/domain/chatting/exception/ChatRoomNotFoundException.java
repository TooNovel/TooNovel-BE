package com.yju.toonovel.domain.chatting.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class ChatRoomNotFoundException extends BusinessException {
	public ChatRoomNotFoundException() {
		super(ErrorCode.CHAT_ROOM_NOT_FOUNT);
	}
}
