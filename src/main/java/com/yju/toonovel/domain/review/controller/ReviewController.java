package com.yju.toonovel.domain.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.domain.review.dto.AllReviewInWorkResponseDto;
import com.yju.toonovel.domain.review.dto.ReviewAllByUserDto;
import com.yju.toonovel.domain.review.dto.ReviewRegisterRequestDto;
import com.yju.toonovel.domain.review.service.LikeReviewService;
import com.yju.toonovel.domain.review.service.ReviewService;
import com.yju.toonovel.global.security.jwt.JwtAuthentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

	private final ReviewService reviewService;
	private final LikeReviewService likeReviewService;

	//리뷰등록
	@PostMapping
	public void registerReview(@RequestBody ReviewRegisterRequestDto dto,
		@AuthenticationPrincipal JwtAuthentication user) {
		reviewService.reviewRegister(dto, user.userId);
	}

	//리뷰 삭제
	@DeleteMapping("/{rid}")
	public void deleteReview(@PathVariable("rid") Long rid,
		@AuthenticationPrincipal JwtAuthentication user) {
		reviewService.reviewDelete(rid, user.userId);
	}

	//전체리뷰조회
	@GetMapping("{startPage}")
	public Page<ReviewAllByUserDto> getAllReviewPaging(@PathVariable("startPage") int startPage) {
		return reviewService.getAllReview(startPage);
	}

	//좋아요 등록 기능
	@PostMapping("/{rid}/like")
	public void registerReviewLike(@PathVariable("rid") Long rid,
		@AuthenticationPrincipal JwtAuthentication user) {

		likeReviewService.clickReviewLike(rid, user.userId);
	}

	//한작품에 있는 리뷰 전체조회(좋아요까지)
	@GetMapping("/{nid}/novel")
	public Page<AllReviewInWorkResponseDto> reviewAllListWithLike(@PathVariable("nid") Long nid) {
		Page<AllReviewInWorkResponseDto> reviewAllList = reviewService.getAllReviewWithLike(nid);

		return reviewAllList;
	}

	//유저가 작성한 리뷰 조회
	@GetMapping("/myReview")
	public Page<ReviewAllByUserDto> getAllReviewByUser(@AuthenticationPrincipal JwtAuthentication user) {
		Page<ReviewAllByUserDto> reviewList = reviewService.getAllReviewByUser(user.userId);

		return reviewList;
	}

	//장르별 전체리뷰조회
	// @GetMapping("/{genre}/genre")
	// public Page<ReviewAllByUserDto> getAllReviewWhereGenre(@PathVariable("genre") String genre) {
	//
	// 	return reviewService.getAllReviewWhereGenre(genre);
	// }
}
