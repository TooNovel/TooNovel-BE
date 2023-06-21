package com.yju.toonovel.domain.statistics.repository;

import java.util.List;

import com.yju.toonovel.domain.admin.dto.AdminStatisticsRequestDto;
import com.yju.toonovel.domain.statistics.dto.AdminStatisticsResponseDto;
import com.yju.toonovel.domain.statistics.dto.StatisticsResultResponseDto;

public interface StatisticRepositoryCustom {

	List<StatisticsResultResponseDto> getGenderStatistic(Long nid);

	List<StatisticsResultResponseDto> getAgeStatistic(Long nid);

	List<AdminStatisticsResponseDto> getReviewStatistic(AdminStatisticsRequestDto dto);

	List<AdminStatisticsResponseDto> getNovelStatistic(AdminStatisticsRequestDto dto);
}
