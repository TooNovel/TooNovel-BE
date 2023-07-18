package com.yju.toonovel.domain.statistics.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatisticsResultResponseDto {

	private Long count;
	private String gender;
	private String age;
}
