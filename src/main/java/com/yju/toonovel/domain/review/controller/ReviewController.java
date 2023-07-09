package com.yju.toonovel.domain.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.review.dto.ReviewByNovelResponseDto;
import com.yju.toonovel.domain.review.dto.ReviewByUserResponseDto;
import com.yju.toonovel.domain.review.dto.ReviewPaginationRequestDto;
import com.yju.toonovel.domain.review.dto.ReviewRegisterRequestDto;
import com.yju.toonovel.domain.review.service.LikeReviewService;
import com.yju.toonovel.domain.review.service.ReviewService;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "리뷰 API")
@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	private final LikeReviewService likeReviewService;

	@Operation(summary = "리뷰 등록 요청")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "이미 해당 소설에 대해 리뷰를 작성한 상태일 때")
	@ApiResponse(responseCode = "404", description = "해당 유저나 소설이 없는 상태일 때")
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public void registerReview(@RequestBody ReviewRegisterRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		reviewService.reviewRegister(dto, user.userId);
	}

	@Operation(summary = "리뷰 삭제 요청")
	@ApiResponse(responseCode = "204", description = "요청 성공")
	@ApiResponse(responseCode = "403", description = "요청한 유저와 리뷰의 작성자가 다른 상태일 때")
	@ApiResponse(responseCode = "404", description = "해당 유저나 소설이 없는 상태일 때")
	@DeleteMapping("/{rid}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteReview(@PathVariable("rid") Long rid,
		@AuthenticationPrincipal JwtAuthentication user) {
		reviewService.reviewDelete(rid, user.userId);
	}

	@Operation(summary = "리뷰 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public Page<ReviewByUserResponseDto> getAllReview(@ModelAttribute ReviewPaginationRequestDto requestDto) {
		return reviewService.findAllReview(requestDto);
	}

	@Operation(summary = "리뷰 좋아요 요청")
	@ApiResponse(responseCode = "201", description = "요청 성공")
	@ApiResponse(responseCode = "400", description = "이미 좋아요를한 상태일 때")
	@ApiResponse(responseCode = "404", description = "해당 유저나 소설이 없는 상태일 때")
	@PostMapping("/{rid}/like")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerReviewLike(@PathVariable("rid") Long rid,
		@AuthenticationPrincipal JwtAuthentication user) {

		likeReviewService.clickReviewLike(rid, user.userId);
	}

	@Operation(summary = "특정 소설에 속한 리뷰 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@GetMapping("/{nid}/novel")
	@ResponseStatus(HttpStatus.OK)
	public Page<ReviewByNovelResponseDto> getAllReviewByNovel(@PathVariable("nid") Long nid,
		@ModelAttribute ReviewPaginationRequestDto requestDto) {

		return reviewService.findAllReviewByNovel(nid, requestDto);
	}

	@Operation(summary = "유저가 작성한 조회 요청")
	@ApiResponse(responseCode = "200", description = "요청 성공")
	@ApiResponse(responseCode = "404", description = "해당 유저나 소설이 없는 상태일 때")
	@GetMapping("/myReview")
	@ResponseStatus(HttpStatus.OK)
	public Page<ReviewByUserResponseDto> getAllReviewByUser(@AuthenticationPrincipal JwtAuthentication user,
		@ModelAttribute ReviewPaginationRequestDto requestDto) {

		return reviewService.findAllReviewByUser(user.userId, requestDto);
	}
}
