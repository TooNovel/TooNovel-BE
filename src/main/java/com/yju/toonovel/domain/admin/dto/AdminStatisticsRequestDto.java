package com.yju.toonovel.domain.admin.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "관리자 통계 데이터 날짜 요청 DTO")
@Getter
public class AdminStatisticsRequestDto {

	@Schema(description = "조회하고 싶은 날짜의 시작날짜")
	private final String startDate;
	@Schema(description = "조회하고 싶은 날짜의 끝날짜")
	private final String endDate;

	@Builder
	private AdminStatisticsRequestDto(String startDate, String endDate) {
		this.startDate = startDate == null ? oneWeekAgoDate() : startDate;
		this.endDate = endDate == null ? todayDate() : endDate;
	}

	public static AdminStatisticsRequestDto from(AdminStatisticsRequestDto dto) {
		return AdminStatisticsRequestDto.builder()
			.startDate(dto.getStartDate())
			.endDate(dto.getEndDate())
			.build();
	}

	private String todayDate() {
		LocalDateTime today = LocalDateTime.now();
		return today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	private String oneWeekAgoDate() {
		LocalDateTime today = LocalDateTime.now().minusWeeks(1);
		return today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
}
