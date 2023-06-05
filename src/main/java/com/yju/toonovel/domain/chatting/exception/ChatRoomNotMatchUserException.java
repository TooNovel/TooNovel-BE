package com.yju.toonovel.domain.chatting.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class ChatRoomNotMatchUserException extends BusinessException {
	public ChatRoomNotMatchUserException() {
		super(ErrorCode.CHAT_ROOM_NOT_MATCH_USER);
	}
}
