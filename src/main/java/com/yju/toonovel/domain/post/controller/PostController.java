package com.yju.toonovel.domain.post.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/read/{pid}")
	@ResponseBody
	public PostResponseDto read(@PathVariable(value = "pid") Long pid) {
		return communityService.readPost(pid);
	}

	@PatchMapping("/update")
	public void uppdate(@RequestBody PostRequestDto dto) {
		communityService.updatePost(dto);
	}

	@GetMapping("/readall")
	@ResponseBody
	public List<Post> readAll() throws InterruptedException {
		return communityService.readPostAll();
	}

	@DeleteMapping("/delete")
	public void delete(@RequestParam(value = "pid") Long pid) {
		communityService.deletePost(pid);
	}
}
