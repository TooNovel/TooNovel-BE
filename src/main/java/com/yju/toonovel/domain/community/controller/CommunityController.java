package com.yju.toonovel.domain.community.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.community.dto.CommunityRequestDto;
import com.yju.toonovel.domain.community.service.CommunityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

	private final CommunityService communityService;

	@PostMapping("/write")
	public void write(@RequestBody CommunityRequestDto dto) {
		communityService.addPost(dto);
	}
}
