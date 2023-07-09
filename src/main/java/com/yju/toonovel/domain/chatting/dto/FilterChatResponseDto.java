package com.yju.toonovel.domain.chatting.dto;

import lombok.Getter;

@Getter
public class FilterChatResponseDto {

	private final String filteredResult;

	public FilterChatResponseDto(String filteredResult) {
		this.filteredResult = filteredResult;
	}
}
