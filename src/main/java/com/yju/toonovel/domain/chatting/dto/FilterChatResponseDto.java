package com.yju.toonovel.domain.chatting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FilterChatResponseDto {

	private String filteredResult;

	@Builder
	public FilterChatResponseDto(String filteredResult) {
		this.filteredResult = filteredResult;
	}
}
