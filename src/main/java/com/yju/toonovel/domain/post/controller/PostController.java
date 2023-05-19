package com.yju.toonovel.domain.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.post.dto.PostAllResponseDto;
import com.yju.toonovel.domain.post.dto.PostPaginationRequestDto;
import com.yju.toonovel.domain.post.dto.PostRegisterRequestDto;
import com.yju.toonovel.domain.post.dto.PostUpdateRequestDto;
import com.yju.toonovel.domain.post.service.LikePostService;
import com.yju.toonovel.domain.post.service.PostService;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final LikePostService likePostService;

	// 게시글 작성
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void postRegister(@RequestBody PostRegisterRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		postService.postRegister(dto, user.userId);
	}

	// 게시글 부분 조회
	@GetMapping("/{pid}")
	@ResponseStatus(HttpStatus.OK)
	public PostAllResponseDto getPost(@PathVariable(value = "pid") Long pid) {
		return postService.getPost(pid);
	}

	// 게시글 수정
	@PatchMapping("/{pid}")
	@ResponseStatus(HttpStatus.CREATED)
	public void postUpdate(@RequestBody PostUpdateRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		postService.updatePost(dto, user.userId);
	}

	// 게시글 전체 조회
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public Page<PostAllResponseDto> getAllPost(@ModelAttribute PostPaginationRequestDto requestDto) {
		return postService.getAllPost(requestDto);
	}

	// 게시글 삭제

	@DeleteMapping("/{pid}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void postDelete(@PathVariable(value = "pid") Long pid, @AuthenticationPrincipal JwtAuthentication user) {
		postService.deletePost(pid, user.userId);
	}

	// 게시글 좋아요 기능
	@PostMapping("/{pid}/like")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerLikePost(@PathVariable("pid") Long pid,
		@AuthenticationPrincipal JwtAuthentication user) {
		likePostService.postLikeRegister(user.userId, pid);
	}

}
