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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.post.dto.PostAllResponseDto;
import com.yju.toonovel.domain.post.dto.PostPaginationRequestDto;
import com.yju.toonovel.domain.post.dto.PostRegisterRequestDto;
import com.yju.toonovel.domain.post.dto.PostResponseDto;
import com.yju.toonovel.domain.post.dto.PostUpdateRequestDto;
import com.yju.toonovel.domain.post.dto.UserLikeCheckPostResponseDto;
import com.yju.toonovel.domain.post.service.LikePostService;
import com.yju.toonovel.domain.post.service.PostService;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "게시글 API")
@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final LikePostService likePostService;

	@Operation(summary = "게시글 등록 요청")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 유저가 없는 상태일 때")
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void postRegister(@RequestBody PostRegisterRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		postService.postRegister(dto, user.userId);
	}

	@Operation(summary = "게시글 상세 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 게시글이 없는 상태일 때")
	@GetMapping("/{pid}")
	@ResponseStatus(HttpStatus.OK)
	public PostResponseDto getPost(@PathVariable(value = "pid") Long pid) {
		return postService.getPost(pid);
	}

	@Operation(summary = "게시글 수정 요청")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 유저나 게시글이 없는 상태일 때")
	@PatchMapping("/{pid}")
	@ResponseStatus(HttpStatus.CREATED)
	public void postUpdate(@RequestBody PostUpdateRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user, @PathVariable(value = "pid") Long pid) {
		postService.updatePost(dto, user.userId, pid);
	}

	@Operation(summary = "게시글 전체 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public Page<PostAllResponseDto> getAllPost(@ModelAttribute PostPaginationRequestDto requestDto) {
		return postService.getAllPost(requestDto);
	}

	@Operation(summary = "게시글 삭제 요청")
	@ApiResponse(responseCode = "204", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 유저나 게시글이 없는 상태일 때")
	@DeleteMapping("/{pid}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void postDelete(@PathVariable(value = "pid") Long pid, @AuthenticationPrincipal JwtAuthentication user) {
		postService.deletePost(pid, user.userId);
	}

	@Operation(summary = "게시글 좋아요 요청")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "이미 좋아요를 한 상태일 때")
	@ApiResponse(responseCode = "404", description = "해당 유저나 게시글이 없는 상태일 때")
	@PutMapping("/{pid}/like")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerLikePost(@PathVariable("pid") Long pid,
		@AuthenticationPrincipal JwtAuthentication user) {
		likePostService.postLikeRegister(user.userId, pid);
	}

	@Operation(summary = "게시글에 좋아요를 한 상태인지 체크 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 게시글이 없는 상태일 때")
	@GetMapping("/{pid}/like")
	@ResponseStatus(HttpStatus.OK)
	public UserLikeCheckPostResponseDto isUserLikes(@PathVariable("pid") Long pid,
		@AuthenticationPrincipal JwtAuthentication user) {
		return likePostService.isUserLikes(user.userId, pid);
	}

}
