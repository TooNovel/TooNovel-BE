package com.yju.toonovel.domain.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.novel.entity.Novel;
import com.yju.toonovel.domain.novel.exception.NovelNotFoundException;
import com.yju.toonovel.domain.novel.repository.NovelRepository;
import com.yju.toonovel.domain.review.dto.ReviewByNovelResponseDto;
import com.yju.toonovel.domain.review.dto.ReviewByUserResponseDto;
import com.yju.toonovel.domain.review.dto.ReviewPaginationRequestDto;
import com.yju.toonovel.domain.review.dto.ReviewRegisterRequestDto;
import com.yju.toonovel.domain.review.entity.Review;
import com.yju.toonovel.domain.review.exception.DuplicateReviewException;
import com.yju.toonovel.domain.review.exception.ReviewNotFoundException;
import com.yju.toonovel.domain.review.exception.ReviewNotMatchUserException;
import com.yju.toonovel.domain.review.repository.ReviewCustomRepositoryImpl;
import com.yju.toonovel.domain.review.repository.ReviewRepository;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final NovelRepository novelRepository;
	private final ReviewCustomRepositoryImpl reviewRepositoryImpl;

	//리뷰 등록
	@Transactional
	public void reviewRegister(ReviewRegisterRequestDto dto, Long userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		Novel novel = novelRepository.findByNovelId(dto.getNovelId())
			.orElseThrow(() -> new NovelNotFoundException());

		reviewRepository.findReviewByNovelIdAndUserId(user.getUserId(), novel.getNovelId())
			.ifPresentOrElse(
				isReview -> {
					throw new DuplicateReviewException();
				},
				() -> {
					reviewRepository.save(Review.of(dto, user, novel));
				});
	}

	//전체 리뷰 조회
	@Transactional(readOnly = true)
	public Page<ReviewByUserResponseDto> findAllReview(ReviewPaginationRequestDto requestDto) {
		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getLimit());
		return reviewRepositoryImpl.findAllReview(pageable, requestDto);
	}

	//리뷰 삭제
	@Transactional
	public void reviewDelete(Long reviewId, Long userId) {
		reviewRepository.findByReviewId(reviewId)
			.ifPresentOrElse(
				review -> validateReviewDelete(review, userId),
				() -> {
					throw new ReviewNotFoundException();
				}
			);
	}

	private void validateReviewDelete(Review review, Long userId) {
		if (userId.equals(review.getWriter().getUserId())) {
			reviewRepository.deleteByReviewId(review.getReviewId());
		} else {
			throw new ReviewNotMatchUserException();
		}
	}

	//한작품에 있는 리뷰 전체조회
	@Transactional(readOnly = true)
	public Page<ReviewByNovelResponseDto> findAllReviewByNovel(Long nid, ReviewPaginationRequestDto requestDto) {

		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getLimit());

		return reviewRepositoryImpl.findAllReviewByNovel(nid, pageable, requestDto);
	}

	//유저가 작성한 리뷰 조회
	@Transactional(readOnly = true)
	public Page<ReviewByUserResponseDto> findAllReviewByUser(Long uid, ReviewPaginationRequestDto requestDto) {
		userRepository.findByUserId(uid)
			.orElseThrow(() -> new UserNotFoundException());

		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getLimit());

		return reviewRepositoryImpl.findAllReviewByUser(uid, pageable, requestDto);
	}
}
