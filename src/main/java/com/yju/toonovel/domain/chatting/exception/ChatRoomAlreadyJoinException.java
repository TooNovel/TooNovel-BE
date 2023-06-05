package com.yju.toonovel.domain.chatting.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class ChatRoomAlreadyJoinException extends BusinessException {
	public ChatRoomAlreadyJoinException() {
		super(ErrorCode.CHAT_ROOM_ALREADY_JOIN);
	}
}
