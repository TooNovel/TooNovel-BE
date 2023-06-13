package com.yju.toonovel.domain.chatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FilterChatRequestDto {

	private String message;

	@Builder
	public FilterChatRequestDto(String message) {
		this.message = message;
	}
}
