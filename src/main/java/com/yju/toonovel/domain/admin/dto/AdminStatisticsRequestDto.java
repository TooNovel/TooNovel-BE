package com.yju.toonovel.domain.admin.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminStatisticsRequestDto {

	private String startDate;
	private String endDate;

	@Builder
	public AdminStatisticsRequestDto(String startDate, String endDate) {
		this.startDate = startDate == null ? oneWeekAgoDate() : startDate;
		this.endDate = endDate == null ? todayDate() : endDate;
	}

	public String todayDate() {
		LocalDateTime today = LocalDateTime.now();
		return today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public String oneWeekAgoDate() {
		LocalDateTime today = LocalDateTime.now().minusWeeks(1);
		return today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
}
