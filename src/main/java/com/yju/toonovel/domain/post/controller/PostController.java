package com.yju.toonovel.domain.post.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.post.dto.PostRequestDto;
import com.yju.toonovel.domain.post.dto.PostResponseDto;
import com.yju.toonovel.domain.post.entity.Post;
import com.yju.toonovel.domain.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

	private final PostService communityService;

	@PostMapping("/write")
	public void write(@RequestBody PostRequestDto dto) {
		communityService.addPost(dto);
	}

	@GetMapping("/read")
	@ResponseBody
	public PostResponseDto read(@RequestParam(value = "pid") Long pid) {
		// System.out.println(pid);
		return communityService.readPost(pid);
	}

	@PutMapping("/update")
	public void uppdate(@RequestBody PostRequestDto dto) {
		communityService.updatePost(dto);
	}

	@GetMapping("/readall")
	@ResponseBody
	public List<Post> readAll() {
		return communityService.readPostAll();
	}
}
