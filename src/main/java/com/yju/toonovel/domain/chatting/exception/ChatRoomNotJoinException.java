package com.yju.toonovel.domain.chatting.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class ChatRoomNotJoinException extends BusinessException {
	public ChatRoomNotJoinException() {
		super(ErrorCode.CHAT_ROOM_NOT_JOIN);
	}
}
