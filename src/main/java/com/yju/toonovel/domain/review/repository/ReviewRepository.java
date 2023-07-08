package com.yju.toonovel.domain.review.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yju.toonovel.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	Optional<Review> findByReviewId(Long rid);

	@Query("select r from Review r where r.writer.userId = :userId and r.novel.novelId = :novelId")
	Optional<Review> findReviewByNovelIdAndUserId(@Param("userId") Long userId, @Param("novelId") Long novelId);

	@Modifying
	@Query("delete from Review r where r.reviewId = :reviewId")
	void deleteByReviewId(@Param("reviewId") Long reviewId);
}
