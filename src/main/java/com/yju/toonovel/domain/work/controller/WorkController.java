package com.yju.toonovel.domain.work.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.work.dto.WorkResponseDto;
import com.yju.toonovel.domain.work.service.WorkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/work")
@RequiredArgsConstructor
public class WorkController {

	private final WorkService workService;

	@GetMapping("/readall")
	public List<WorkResponseDto> allWork() {
		return workService.readAll();
	}

}
