package com.yju.toonovel.domain.review.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yju.toonovel.domain.review.entity.LikeReview;
import com.yju.toonovel.domain.review.entity.Review;
import com.yju.toonovel.domain.user.entity.User;

public interface LikeReviewRepository extends JpaRepository<LikeReview, Long> {

	@Query("select l from LikeReview l where l.review = :review and l.user = :user")
	Optional<LikeReview> findByUserIdAndReviewId(@Param("user") User user, @Param("review") Review review);
}
