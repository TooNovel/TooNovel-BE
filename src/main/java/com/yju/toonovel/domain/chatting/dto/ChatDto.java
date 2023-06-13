package com.yju.toonovel.domain.chatting.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "채팅 정보 요청/응답 DTO")
@Getter
@ToString
@Setter
@NoArgsConstructor
public class ChatDto {
	@Schema(description = "채팅 ID")
	private Long chatId;
	@Schema(description = "채팅 작성자 이름")
	private String senderName;
	@Schema(description = "채팅 작성자 userId")
	private Long senderId;
	@Schema(description = "채팅 작성자가 채팅방의 주인인지")
	private boolean isCreator;
	@NotBlank
	@Length(max = 300)
	@Schema(description = "채팅 내용")
	private String message;

	@Schema(description = "메시지 필터링 결과 'ok' or 'bad'")
	private String filterResult;

	@Builder
	public ChatDto(Long chatId, String senderName, Long senderId, boolean isCreator, String message) {
		this.chatId = chatId;
		this.senderName = senderName;
		this.senderId = senderId;
		this.isCreator = isCreator;
		this.message = message;
	}

	public static ChatDto of(Long chatId, String senderName, Long senderId, boolean isCreator, String message) {
		// chatId = no offset 페이징용, Reply시 식별용
		// senderName = 채팅방에 발신자 닉네임 표시
		// senderId = 수신자가 자신이 보낸 메세지인지 구분
		// isCreator = 해당 채팅방을 생성한 작가 보낸건지 구분
		// message = 메시지 내용
		return ChatDto.builder()
			.chatId(chatId)
			.senderName(senderName)
			.senderId(senderId)
			.isCreator(isCreator)
			.message(message)
			.build();
	}
}
