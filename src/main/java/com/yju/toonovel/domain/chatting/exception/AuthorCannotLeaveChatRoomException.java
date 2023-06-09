package com.yju.toonovel.domain.chatting.exception;

import com.yju.toonovel.global.error.exception.BusinessException;
import com.yju.toonovel.global.error.exception.ErrorCode;

public class AuthorCannotLeaveChatRoomException extends BusinessException {
	public AuthorCannotLeaveChatRoomException() {
		super(ErrorCode.AUTHOR_CANNOT_LEAVE_CHATROOM);
	}
}
