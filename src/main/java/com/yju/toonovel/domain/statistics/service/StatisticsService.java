package com.yju.toonovel.domain.statistics.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.novel.entity.Novel;
import com.yju.toonovel.domain.novel.exception.NovelNotFoundException;
import com.yju.toonovel.domain.novel.exception.NovelNotMatchAuthorException;
import com.yju.toonovel.domain.novel.repository.NovelRepository;
import com.yju.toonovel.domain.statistics.dto.AdminStatisticsResponseDto;
import com.yju.toonovel.domain.statistics.dto.StatisticsResultResponseDto;
import com.yju.toonovel.domain.statistics.repository.StatisticRepositoryImpl;
import com.yju.toonovel.domain.statistics.repository.StatisticsRepository;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticsService {

	private final NovelRepository novelRepository;
	private final StatisticRepositoryImpl statisticRepositoryImpl;
	private final UserRepository userRepository;
	private final StatisticsRepository statisticsRepository;

	@Transactional(readOnly = true)
	public List<StatisticsResultResponseDto> getGenderStatistics(Long nid, Long uid) {
		User user = userRepository.findByUserId(uid)
			.orElseThrow(()-> new UserNotFoundException());

		novelRepository.findByNovelId(nid)
			.orElseThrow(() -> new NovelNotFoundException());

		Novel novel = statisticsRepository.findNovelByUserIdAndNovelId(nid, user.getUserId())
			.orElseThrow(()-> new NovelNotMatchAuthorException());

		return statisticRepositoryImpl.getGenderStatistic(novel.getNovelId());
	}

	@Transactional(readOnly = true)
	public List<StatisticsResultResponseDto> getAgeStatistics(Long nid, Long uid) {
		User user = userRepository.findByUserId(uid)
			.orElseThrow(()-> new UserNotFoundException());

		novelRepository.findByNovelId(nid)
			.orElseThrow(() -> new NovelNotFoundException());

		Novel novel = statisticsRepository.findNovelByUserIdAndNovelId(nid, user.getUserId())
			.orElseThrow(()-> new NovelNotMatchAuthorException());

		return statisticRepositoryImpl.getAgeStatistic(novel.getNovelId());
	}

	public List<AdminStatisticsResponseDto> getReviewStatistic() {

		return statisticRepositoryImpl.getReviewStatistic();
	}

	public List<AdminStatisticsResponseDto> getNovelStatistic() {

		return statisticRepositoryImpl.getNovelStatistic();
	}
}
