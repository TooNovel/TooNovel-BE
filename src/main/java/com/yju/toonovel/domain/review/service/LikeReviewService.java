package com.yju.toonovel.domain.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yju.toonovel.domain.review.entity.LikeReview;
import com.yju.toonovel.domain.review.entity.Review;
import com.yju.toonovel.domain.review.exception.ReviewAlreadyLikedException;
import com.yju.toonovel.domain.review.exception.ReviewNotFoundException;
import com.yju.toonovel.domain.review.repository.LikeReviewRepository;
import com.yju.toonovel.domain.review.repository.ReviewRepository;
import com.yju.toonovel.domain.user.entity.User;
import com.yju.toonovel.domain.user.exception.UserNotFoundException;
import com.yju.toonovel.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeReviewService {

	private final LikeReviewRepository likeReviewRepository;
	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;

	//좋아요 등록 기능 + 좋아요 카운트 증가
	@Transactional
	public void clickReviewLike(Long rid, Long uid) {

		User user = userRepository.findByUserId(uid)
			.orElseThrow(() -> new UserNotFoundException());

		Review review = reviewRepository.findByReviewId(rid)
			.orElseThrow(() -> new ReviewNotFoundException());

		LikeReview likeReview = findUserLikeReview(user, review);

		if (likeReview.isActived()) {
			throw new ReviewAlreadyLikedException();
		}
		likeReview.toggleLike();

		//좋아요 카운트 증가
		review.clickReviewLike();
	}

	//이미 좋아요가 되어있는지 체크
	public LikeReview findUserLikeReview(User user, Review review) {
		return likeReviewRepository.findByUserIdAndReviewId(user, review)
			.orElseGet(() -> likeReviewRepository.save(LikeReview.likeReview(user, review)));
	}
}
