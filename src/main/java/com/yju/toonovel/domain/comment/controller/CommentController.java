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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "댓글 API")
@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;
	private final LikeCommentService likeCommentService;

	@Operation(summary = "댓글 작성")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "유저나 게시글이 없는 상태일 때")
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void commentRegister(@AuthenticationPrincipal JwtAuthentication user,
		@RequestBody CommentRegisterRequestDto dto) {
		commentService.commentRegister(dto, user.userId);
	}

	@Operation(summary = "댓글 삭제")
	@ApiResponse(responseCode = "204", description = "요청 성공")
	@ApiResponse(responseCode = "403", description = "요청한 유저와 댓글의 작성자가 다른 상태일 때")
	@ApiResponse(responseCode = "404", description = "해당 댓글이 없는 상태일 때")
	@DeleteMapping("/{cid}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void commentDelete(@PathVariable("cid") Long cid, @AuthenticationPrincipal JwtAuthentication user) {
		commentService.deleteComment(cid, user.userId);
	}

	@Operation(summary = "댓글 수정")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "403", description = "요청한 유저와 댓글의 작성자가 다른 상태일 때")
	@ApiResponse(responseCode = "404", description = "해당 유저나 게시글이 없는 상태일 때")
	@PatchMapping("/{cid}")
	@ResponseStatus(HttpStatus.CREATED)
	public void commentUpdate(@PathVariable("cid") Long cid, @AuthenticationPrincipal JwtAuthentication user,
		@RequestBody CommentUpdateRequestDto dto
	) {
		commentService.updateComment(dto, cid, user.userId);
	}

	@Operation(summary = "댓글 조회", description = "해당 게시글에 달린 댓글을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@GetMapping("/{pid}")
	@ResponseStatus(HttpStatus.OK)
	public Page<CommentAllResponseDto> getAllCommentByPost(@PathVariable("pid") Long pid,
		@ModelAttribute CommentPaginationRequestDto requestDto) {
		return commentService.getAllCommentByPost(pid, requestDto);
	}

	@Operation(summary = "댓글 좋아요")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "이미 좋아요를 한 상태일 때")
	@ApiResponse(responseCode = "404", description = "해당 유저나 댓글이 없는 상태일 때")
	@PostMapping("/{cid}/like")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerLikeComment(@PathVariable("cid") Long cid,
		@AuthenticationPrincipal JwtAuthentication user) {
		likeCommentService.commentLikeRegister(user.userId, cid);
	}
}
