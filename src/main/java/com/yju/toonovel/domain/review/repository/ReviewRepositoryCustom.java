package com.yju.toonovel.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yju.toonovel.domain.review.dto.ReviewAllByUserDto;
import com.yju.toonovel.domain.review.dto.ReviewByNovelResponseDto;
import com.yju.toonovel.domain.review.dto.ReviewPaginationRequestDto;

public interface ReviewRepositoryCustom {

	//유저가 작성한 리뷰조회
	Page<ReviewAllByUserDto> findAllReviewByUser(Long uid, Pageable pageable, ReviewPaginationRequestDto requestDto);

	//전체리뷰조회
	Page<ReviewAllByUserDto> getAllReview(Pageable pageable, ReviewPaginationRequestDto requestDto);

	//한 작품안의 리뷰 전체 조회
	Page<ReviewByNovelResponseDto> getReviewByNovel(Long nid, Pageable pageable,
		ReviewPaginationRequestDto requestDto);
}

