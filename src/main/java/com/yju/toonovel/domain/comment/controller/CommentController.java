package com.yju.toonovel.domain.comment.controller;

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

import com.yju.toonovel.domain.comment.dto.CommentAllResponseDto;
import com.yju.toonovel.domain.comment.dto.CommentPaginationRequestDto;
import com.yju.toonovel.domain.comment.dto.CommentRegisterRequestDto;
import com.yju.toonovel.domain.comment.dto.CommentUpdateRequestDto;
import com.yju.toonovel.domain.comment.service.CommentService;
import com.yju.toonovel.domain.comment.service.LikeCommentService;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;
	private final LikeCommentService likeCommentService;

	//댓글 작성
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void commentRegister(@RequestBody CommentRegisterRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		commentService.commentRegister(dto, user.userId);
	}

	//댓글 삭제
	@DeleteMapping("/{cid}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void commentDelete(@PathVariable("cid") Long cid, @AuthenticationPrincipal JwtAuthentication user) {
		commentService.deleteComment(cid, user.userId);
	}

	//댓글 수정(내용만 수정 가능)
	@PatchMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void commentUpdate(@RequestBody CommentUpdateRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		commentService.updateComment(dto, user.userId);
	}

	//한 게시글에 대한 댓글 조회
	@GetMapping("/{pid}")
	public Page<CommentAllResponseDto> getAllCommentByPost(
		@ModelAttribute CommentPaginationRequestDto requestDto,
		@PathVariable("pid") Long pid) {
		return commentService.getAllCommentByPost(pid, requestDto);
	}

	//댓글 좋아요 기능
	@PostMapping("/{cid}/like")
	public void registerLikeComment(@PathVariable("cid") Long cid,
		@AuthenticationPrincipal JwtAuthentication user) {
		likeCommentService.commentLikeRegister(user.userId, cid);
	}
}
