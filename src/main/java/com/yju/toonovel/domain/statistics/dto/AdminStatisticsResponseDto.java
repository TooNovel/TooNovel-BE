package com.yju.toonovel.domain.statistics.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminStatisticsResponseDto {

	private Long count;
	private String createdDate;

	@Builder
	public AdminStatisticsResponseDto(Long count, String createdDate) {
		this.count = count;
		this.createdDate = createdDate;
	}
}
