package com.yju.toonovel.domain.review.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yju.toonovel.domain.review.dto.AllReviewInWorkResponseDto;
import com.yju.toonovel.domain.review.dto.ReviewAllByUserDto;
import com.yju.toonovel.domain.review.dto.ReviewPaginationRequestDto;

public interface ReviewRepositoryCustom {

	//유저가 작성한 리뷰조회
	Page<ReviewAllByUserDto> findAllReviewByUser(Long uid, Pageable pageable);

	//전체리뷰조회
	Page<ReviewAllByUserDto> getAllReview(Pageable pageable, ReviewPaginationRequestDto requestDto);
}

