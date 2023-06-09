package com.yju.toonovel.domain.chatting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyDto {
	private Long replyId;
	private Long senderId;
	private String senderName;
	private String replyMessage;
	private Long chatId;
	private String userMessage;
	private String userName;
}
