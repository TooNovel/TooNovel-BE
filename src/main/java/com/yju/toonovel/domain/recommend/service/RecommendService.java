package com.yju.toonovel.domain.recommend.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.yju.toonovel.domain.novel.dto.NovelPaginationResponseDto;
import com.yju.toonovel.domain.novel.repository.NovelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendService {

	private final NovelRepository novelRepository;

	@Value("${server.machineLearning}")
	private String machineLearningServer;

	@Transactional
	public List<NovelPaginationResponseDto> getNovelRecommend(Long userId) {

		RestTemplate restTemplate = new RestTemplate();

		URI uri = UriComponentsBuilder
			.fromUriString(machineLearningServer)
			.path("/recommend/" + userId)
			.encode()
			.build()
			.toUri();

		Integer[] recommendList = restTemplate.getForObject(uri, Integer[].class);

		List<Long> novelIdList = Optional.ofNullable(recommendList)
			.map(array -> Arrays.stream(array))
			.orElseGet(() -> Stream.empty())
			.map(list -> Long.valueOf(list))
			.collect(Collectors.toList());

		return novelRepository.findNovelsByNovelIdList(novelIdList)
			.stream()
			.map(novel -> NovelPaginationResponseDto.from(novel))
			.collect(Collectors.toList());

	}

}
