package com.yju.toonovel.domain.review.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yju.toonovel.domain.review.dto.ReviewAllByUserDto;
import com.yju.toonovel.domain.review.dto.ReviewPaginationRequestDto;

public interface ReviewRepositoryCustom {

	//유저가 작성한 리뷰조회
	Page<ReviewAllByUserDto> findAllReviewByUser(Long uid, Pageable pageable);

	//전체리뷰조회
	Page<ReviewAllByUserDto> getAllReview(Pageable pageable);

	//장르별 전체리뷰조회
	// Page<ReviewAllByUserDto> getAllReviewWhereGenre(String genre, Pageable pageable);

	//전체리뷰조회 - 좋아요순 정렬
	Page<ReviewAllByUserDto> getAllReviewOrderByLike(Pageable pageable);

	//장르별 전체리뷰조회 - 좋아요순 정렬
	// Page<ReviewAllByUserDto> getAllReviewOrderByLikeWhereGenre(String genre, Pageable pageable);
}

