package com.yju.toonovel.domain.chatting.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "채팅에 대한 답장 요청/응답 DTO")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReplyDto {
	@Schema(description = "답장 번호")
	private Long replyId;
	@Schema(description = "답장을 보낸 작가의 userId")
	private Long senderId;
	@Schema(description = "답장을 보낸 작가의 닉네임")
	private String senderName;
	@NotBlank
	@Length(max = 300)
	@Schema(description = "답장 내용")
	private String replyMessage;
	@Schema(description = "답장을 보낸 시간")
	private LocalDateTime createdDate;
	@Schema(description = "원문 채팅의 채팅 번호")
	private Long chatId;
	@Schema(description = "원문 채팅의 채팅 내용")
	private String userMessage;
	@Schema(description = "원문 채팅을 보낸 유저의 닉네임")
	private String userName;
}
