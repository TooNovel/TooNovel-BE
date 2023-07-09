package com.yju.toonovel.domain.chatting.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FilterChatRequestDto {

	private final String message;

	@Builder
	private FilterChatRequestDto(String message) {
		this.message = message;
	}

	public static FilterChatRequestDto of(String message) {
		return FilterChatRequestDto.builder()
			.message(message)
			.build();
	}
}
