package com.yju.toonovel.domain.community.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.community.dto.CommunityRequestDto;
import com.yju.toonovel.domain.community.repository.CommunityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityService {

	private final CommunityRepository communityRepository;

	public void addPost(CommunityRequestDto dto) {
		communityRepository.save(dto.toEntity());
	}
}
