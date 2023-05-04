package com.yju.toonovel.domain.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.novel.entity.Novel;
import com.yju.toonovel.domain.novel.exception.NovelNotFoundException;
import com.yju.toonovel.domain.novel.repository.NovelRepository;
import com.yju.toonovel.domain.review.dto.AllReviewInWorkResponseDto;
import com.yju.toonovel.domain.review.dto.ReviewAllByUserDto;
import com.yju.toonovel.domain.review.dto.ReviewRegisterRequestDto;
import com.yju.toonovel.domain.review.entity.Review;
import com.yju.toonovel.domain.review.exception.DuplicateReviewException;
import com.yju.toonovel.domain.review.exception.ReviewNotFoundException;
import com.yju.toonovel.domain.review.exception.ReviewNotMatchUserException;
import com.yju.toonovel.domain.review.repository.ReviewRepository;
import com.yju.toonovel.domain.review.repository.ReviewRepositoryImpl;
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
	private final ReviewRepositoryImpl reviewRepositoryImpl;

	//리뷰 등록
	public void reviewRegister(ReviewRegisterRequestDto dto, Long userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new UserNotFoundException());

		Novel novel = novelRepository.findByNovelId(dto.getNovelId())
			.orElseThrow(() -> new NovelNotFoundException());

		reviewRepository.findByReviewId(dto.getReviewId())
			.ifPresentOrElse(
				review1 -> new DuplicateReviewException(),
				() -> {
					reviewRepository.save(dto.dtoToEntity(user, novel));
				});
	}

	//전체 리뷰 조회
	public Page<ReviewAllByUserDto> getAllReview() {
		Pageable pageable = PageRequest.of(0, 10);
		return reviewRepositoryImpl.getAllReview(pageable);
	}

	//장르별 전체리뷰조회
	// public Page<ReviewAllByUserDto> getAllReviewWhereGenre(String genre) {
	// 	String novel = novelRepository.findByGenre(genre)
	// 		.orElseThrow(() -> new GenreNotFoundException());
	//
	// 	Pageable pageable = PageRequest.of(0, 10);
	// 	return reviewRepositoryImpl.getAllReviewWhereGenre(novel, pageable);
	// }

	//리뷰 삭제
	@Transactional
	public void reviewDelete(Long reviewId, Long userId) {
		reviewRepository.findByReviewId(reviewId)
			.ifPresentOrElse(
				review -> validateDelete(reviewId, userId),
				() -> {
					throw new ReviewNotFoundException();
				}
			);
	}

	// 이미 추천했다라는것 - 진입했을때 알필요 x

	public void validateDelete(Long userId, Long reviewId) {
		Review review = reviewRepository.deleteReviewByReviewIdAndUserId(reviewId)
			.orElseThrow(() -> new ReviewNotFoundException());

		log.info("review.getWriter().getUserId()) :" + review.getWriter().getUserId());
		if(userId.equals(review.getWriter().getUserId())) {
			reviewRepository.deleteByReviewId(reviewId);
		} else {
			throw new ReviewNotMatchUserException();
		}
	}

	//한작품에 있는 리뷰 전체조회(좋아요까지)
	public Page<AllReviewInWorkResponseDto> getAllReviewWithLike(Long nid) {

		Pageable pageable = PageRequest.of(0, 10);
		Page<AllReviewInWorkResponseDto> reviewList = reviewRepository
			.getReviewAndReviewLikeByNovelOrderByReviewIdPaging(nid, pageable);

		return reviewList;
	}

	//유저가 작성한 리뷰 조회
	public Page<ReviewAllByUserDto> getAllReviewByUser(Long uid) {
		Pageable pageable = PageRequest.of(0, 10);
		Page<ReviewAllByUserDto> reviewList = reviewRepositoryImpl.findAllReviewByUser(uid, pageable);

		return reviewList;
	}
}
