package com.yju.toonovel.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yju.toonovel.domain.review.dto.ReviewByNovelResponseDto;
import com.yju.toonovel.domain.review.dto.ReviewByUserResponseDto;
import com.yju.toonovel.domain.review.dto.ReviewPaginationRequestDto;

public interface ReviewCustomRepository {

	//유저가 작성한 리뷰조회
	Page<ReviewByUserResponseDto> findAllReviewByUser(Long uid, Pageable pageable,
		ReviewPaginationRequestDto requestDto);

	//전체리뷰조회
	Page<ReviewByUserResponseDto> findAllReview(Pageable pageable, ReviewPaginationRequestDto requestDto);

	//한 작품안의 리뷰 전체 조회
	Page<ReviewByNovelResponseDto> findAllReviewByNovel(Long nid, Pageable pageable,
		ReviewPaginationRequestDto requestDto);
}

