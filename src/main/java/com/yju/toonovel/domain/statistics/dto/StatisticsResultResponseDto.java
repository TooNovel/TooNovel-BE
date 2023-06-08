package com.yju.toonovel.domain.statistics.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatisticsResultResponseDto {

	private Long count;
	private String gender;
	private String age;

	@Builder
	public StatisticsResultResponseDto(Long count, String gender, String age) {
		this.count = count;
		this.gender = gender;
		this.age = age;
	}
}
