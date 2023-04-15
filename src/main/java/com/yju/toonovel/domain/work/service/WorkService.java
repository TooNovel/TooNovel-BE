package com.yju.toonovel.domain.work.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.work.dto.WorkResponseDto;
import com.yju.toonovel.domain.work.repository.WorkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkService {

	private final WorkRepository workRepository;

	public List<WorkResponseDto> readAll() {
		return workRepository.findAll().stream().map(e -> new WorkResponseDto(e)).collect(Collectors.toList());
	}
}
