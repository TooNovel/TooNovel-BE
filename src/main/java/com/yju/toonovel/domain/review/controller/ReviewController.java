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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	private final LikeReviewService likeReviewService;

	//리뷰등록
	@PostMapping("/{novelId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerReview(@RequestBody ReviewRegisterRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user, @PathVariable("novelId") Long novelId) {
		reviewService.reviewRegister(dto, novelId, user.userId);
	}

	//리뷰 삭제
	@DeleteMapping("/{rid}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteReview(@PathVariable("rid") Long rid,
		@AuthenticationPrincipal JwtAuthentication user) {
		reviewService.reviewDelete(rid, user.userId);
	}

	//전체리뷰조회
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public Page<ReviewByUserResponseDto> getAllReviewPaging(@ModelAttribute ReviewPaginationRequestDto requestDto) {
		return reviewService.getAllReview(requestDto);
	}

	//좋아요 등록 기능
	@PostMapping("/{rid}/like")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerReviewLike(@PathVariable("rid") Long rid,
		@AuthenticationPrincipal JwtAuthentication user) {

		likeReviewService.clickReviewLike(rid, user.userId);
	}

	//한작품에 있는 리뷰 전체조회
	@GetMapping("/{nid}/novel")
	@ResponseStatus(HttpStatus.OK)
	public Page<ReviewByNovelResponseDto> reviewAllListWithLike(@PathVariable("nid") Long nid,
		@ModelAttribute ReviewPaginationRequestDto requestDto) {

		return reviewService.getAllReviewWithLike(nid, requestDto);
	}

	//유저가 작성한 리뷰 조회
	@GetMapping("/myReview")
	@ResponseStatus(HttpStatus.OK)
	public Page<ReviewByUserResponseDto> getAllReviewByUser(@AuthenticationPrincipal JwtAuthentication user,
		@ModelAttribute ReviewPaginationRequestDto requestDto) {

		return reviewService.getAllReviewByUser(user.userId, requestDto);
	}
}
