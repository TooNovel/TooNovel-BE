package com.yju.toonovel.domain.chatting.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class ChatRoomAlreadyExistException extends BusinessException {
	public ChatRoomAlreadyExistException() {
		super(ErrorCode.CHAT_ROOM_ALREADY_EXIST);
	}
}
